package com.example.codeRank.service;

import com.example.codeRank.exception.ExecutionTimeoutException;
import com.example.codeRank.model.Language;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.WaitContainerResultCallback;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Service
public class DockerExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(DockerExecutionService.class);

    @Autowired
    private DockerClient dockerClient;

    @Value("${execution.timeout.default}")
    private int defaultTimeout;

    @Value("${execution.timeout.max}")
    private int maxTimeout;

    @Value("${execution.memory.default}")
    private int defaultMemory;

    @Value("${execution.memory.max}")
    private int maxMemory;

    public ExecutionResult executeCode(Language language, String code, String input) {
        String tempDir = createTempDirectory();
        String containerId = null;

        try {
            // Write code to file
            String fileName = getFileName(language);
            File codeFile = new File(tempDir, fileName);
            writeToFile(codeFile, code);

            // Write input if provided
            if (input != null && !input.isEmpty()) {
                File inputFile = new File(tempDir, "input.txt");
                writeToFile(inputFile, input);
            }

            // Create and run container
            long startTime = System.currentTimeMillis();
            containerId = createAndRunContainer(language, tempDir, input != null && !input.isEmpty());

            // Wait for execution with timeout
            int exitCode = waitForContainer(containerId, defaultTimeout);
            long executionTime = System.currentTimeMillis() - startTime;

            // Get output
            String output = getContainerLogs(containerId);

            return new ExecutionResult(
                    true,
                    output,
                    null,
                    (int) executionTime,
                    defaultMemory
            );

        } catch (ExecutionTimeoutException e) {
            return new ExecutionResult(false, null, e.getMessage(), defaultTimeout, 0);
        } catch (Exception e) {
            logger.error("Execution failed", e);
            return new ExecutionResult(false, null, "Execution error: " + e.getMessage(), 0, 0);
        } finally {
            // Cleanup
            if (containerId != null) {
                cleanupContainer(containerId);
            }
            cleanupTempDirectory(tempDir);
        }
    }

    private String createTempDirectory() {
        try {
            Path tempPath = Files.createTempDirectory("coderank_");
            System.out.println("Created temp directory: " + tempPath);
            return tempPath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp directory", e);
        }
    }

    private void writeToFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    private String getFileName(Language language) {
        switch (language) {
            case PYTHON: return "code.py";
            case JAVA: return "Main.java";
            case JAVASCRIPT: return "code.js";
            case C: return "code.cpp";
            default: throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    private String getDockerImage(Language language) {
        switch (language) {
            case PYTHON: return "python:3.11-alpine";
            case JAVA: return "eclipse-temurin:17-jdk";  // No alpine suffix - supports ARM64
            case JAVASCRIPT: return "node:20-alpine";
            case C: return "gcc:latest";
            default: throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    private String[] getCommand(Language language, boolean hasInput) {
        String inputRedirect = hasInput ? " < /app/input.txt" : "";
        switch (language) {
            case PYTHON:
                return new String[]{"sh", "-c", "python /app/code.py" + inputRedirect};
            case JAVA:
                return new String[]{"sh", "-c", "javac /app/Main.java && java -cp /app Main" + inputRedirect};
            case JAVASCRIPT:
                return new String[]{"sh", "-c", "node /app/code.js" + inputRedirect};
            case C:
                return new String[]{"sh", "-c", "g++ /app/code.cpp -o /app/program && /app/program" + inputRedirect};
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    private String createAndRunContainer(Language language, String tempDir, boolean hasInput) {
        Volume volume = new Volume("/app");

        CreateContainerResponse container = dockerClient.createContainerCmd(getDockerImage(language))
                .withCmd(getCommand(language, hasInput))
                .withHostConfig(
            HostConfig.newHostConfig()
                    .withBinds(new Bind(tempDir, volume))
                    .withMemory((long) defaultMemory * 1024 * 1024)
                    .withMemorySwap((long) defaultMemory * 1024 * 1024)
                    .withCpuQuota(50000L)
                    .withNetworkMode("none")
                    .withPidsLimit(50L)
                )
                .withNetworkDisabled(true)
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();
        return container.getId();
    }

    private int waitForContainer(String containerId, int timeoutSeconds) {
        try {
            WaitContainerResultCallback callback = new WaitContainerResultCallback();
            dockerClient.waitContainerCmd(containerId).exec(callback);

            Integer statusCode = callback.awaitStatusCode(timeoutSeconds, TimeUnit.SECONDS);

            if (statusCode == null) {
                dockerClient.killContainerCmd(containerId).exec();
                throw new ExecutionTimeoutException("Execution timeout: Code exceeded " + timeoutSeconds + " seconds limit");
            }

            return statusCode;
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Execution interrupted", e);
        }
    }

    private String getContainerLogs(String containerId) {
        try {
            // Use a StringBuilder to collect logs
            StringBuilder outputBuilder = new StringBuilder();
            StringBuilder errorBuilder = new StringBuilder();

            dockerClient.logContainerCmd(containerId)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .withTailAll()
                    .exec(new com.github.dockerjava.core.command.LogContainerResultCallback() {
                        @Override
                        public void onNext(com.github.dockerjava.api.model.Frame frame) {
                            if (frame != null) {
                                String log = new String(frame.getPayload()).trim();
                                if (frame.getStreamType() == com.github.dockerjava.api.model.StreamType.STDOUT) {
                                    outputBuilder.append(log).append("\n");
                                } else if (frame.getStreamType() == com.github.dockerjava.api.model.StreamType.STDERR) {
                                    errorBuilder.append(log).append("\n");
                                }
                            }
                        }
                    })
                    .awaitCompletion();

            // Combine stdout and stderr (stderr usually contains compilation errors)
            String output = outputBuilder.toString();
            String errors = errorBuilder.toString();

            // Return combined output, prioritizing stdout
            return output.isEmpty() ? errors : output;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "";
        }
    }

    private void cleanupContainer(String containerId) {
        try {
            dockerClient.removeContainerCmd(containerId).withForce(true).exec();
        } catch (Exception e) {
            logger.warn("Failed to remove container: " + containerId, e);
        }
    }

    private void cleanupTempDirectory(String tempDir) {
        try {
            Files.walk(Path.of(tempDir))
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            logger.warn("Failed to delete: " + path, e);
                        }
                    });
        } catch (IOException e) {
            logger.warn("Failed to cleanup temp directory: " + tempDir, e);
        }
    }

    public static class ExecutionResult {
        private final boolean success;
        private final String output;
        private final String error;
        private final int executionTime;
        private final int memoryUsed;

        public ExecutionResult(boolean success, String output, String error, int executionTime, int memoryUsed) {
            this.success = success;
            this.output = output;
            this.error = error;
            this.executionTime = executionTime;
            this.memoryUsed = memoryUsed;
        }

        public boolean isSuccess() { return success; }
        public String getOutput() { return output; }
        public String getError() { return error; }
        public int getExecutionTime() { return executionTime; }
        public int getMemoryUsed() { return memoryUsed; }
    }
}

