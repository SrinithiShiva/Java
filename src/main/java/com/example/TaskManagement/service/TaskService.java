package com.example.TaskManagement.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TaskManagement.dto.AttachmentCreateDTO;
import com.example.TaskManagement.dto.AttachmentResponseDTO;
import com.example.TaskManagement.dto.CommentCreateDTO;
import com.example.TaskManagement.dto.CommentResponseDTO;
import com.example.TaskManagement.dto.TaskCreateDTO;
import com.example.TaskManagement.dto.TaskResponseDTO;
import com.example.TaskManagement.entity.Feedback;
import com.example.TaskManagement.entity.Project;
import com.example.TaskManagement.entity.TaskAttachment;
import com.example.TaskManagement.entity.TaskStatus;
import com.example.TaskManagement.entity.Tasks;
import com.example.TaskManagement.entity.UserTasks;
import com.example.TaskManagement.entity.Users;
import com.example.TaskManagement.repository.FeedbackRepository;
import com.example.TaskManagement.repository.ProjectRepository;
import com.example.TaskManagement.repository.TaskAttachmentRepository;
import com.example.TaskManagement.repository.TaskRepository;
import com.example.TaskManagement.repository.UserRepository;
import com.example.TaskManagement.repository.UserTaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private TaskAttachmentRepository taskAttachmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public TaskResponseDTO createTask(TaskCreateDTO createDTO) {
        Tasks task = new Tasks();
        task.setTaskName(createDTO.getTaskName());
        task.setTaskDescription(createDTO.getTaskDescription());
        task.setDueDate(createDTO.getDueDate());
        task.setTaskStatus(TaskStatus.OPEN);

        if (createDTO.getProjectId() != null) {
            Project project = projectRepository.findById(createDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        Tasks savedTask = taskRepository.save(task);

        if (createDTO.getAssignedUserIds() != null && !createDTO.getAssignedUserIds().isEmpty()) {
            for (Long userId : createDTO.getAssignedUserIds()) {
                Users user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                if (!userTaskRepository.existsByUsersUserIdAndTasksTaskId(userId, savedTask.getTaskId())) {
                    UserTasks userTask = new UserTasks();
                    userTask.setUsers(user);
                    userTask.setTasks(savedTask);
                    userTaskRepository.save(userTask);
                }
            }
        }

        return toTaskResponse(savedTask);
    }

    public List<TaskResponseDTO> getTasksAssignedToUser(Long userId) {
        List<UserTasks> assignments = userTaskRepository.findByUsersUserId(userId);
        return assignments.stream()
                .map(UserTasks::getTasks)
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO updateTaskStatus(Long taskId, TaskStatus status) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTaskStatus(status);
        Tasks saved = taskRepository.save(task);
        return toTaskResponse(saved);
    }

    public TaskResponseDTO assignTask(Long taskId, Long userId) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!userTaskRepository.existsByUsersUserIdAndTasksTaskId(userId, taskId)) {
            UserTasks userTask = new UserTasks();
            userTask.setUsers(user);
            userTask.setTasks(task);
            userTaskRepository.save(userTask);
        }

        return toTaskResponse(task);
    }

    public List<TaskResponseDTO> filterTasksByStatus(TaskStatus status) {
        return taskRepository.findByTaskStatus(status)
                .stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDTO> searchTasks(String query) {
        return taskRepository.findByTaskNameContainingIgnoreCaseOrTaskDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    public CommentResponseDTO addComment(Long taskId, CommentCreateDTO commentDTO) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        Users user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Feedback feedback = new Feedback();
        feedback.setTask(task);
        feedback.setPostedUser(user);
        feedback.setCommentText(commentDTO.getCommentText());
        feedback.setPostTime(new Date());
        Feedback saved = feedbackRepository.save(feedback);
        return toCommentResponse(saved);
    }

    public AttachmentResponseDTO addAttachment(Long taskId, AttachmentCreateDTO attachmentDTO) {
        userRepository.findById(attachmentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        UserTasks userTask = userTaskRepository.findByUsersUserIdAndTasksTaskId(attachmentDTO.getUserId(), taskId)
                .orElseThrow(() -> new RuntimeException("User is not assigned to this task"));

        TaskAttachment attachment = new TaskAttachment();
        attachment.setUserTask(userTask);
        attachment.setFileName(attachmentDTO.getFileName());
        attachment.setFileUrl(attachmentDTO.getFileUrl());
        attachment.setUploadedAt(new Date());
        TaskAttachment saved = taskAttachmentRepository.save(attachment);
        return toAttachmentResponse(saved);
    }

    private TaskResponseDTO toTaskResponse(Tasks task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setDueDate(task.getDueDate());
        dto.setTaskStatus(task.getTaskStatus());
        dto.setProjectId(task.getProject() != null ? task.getProject().getProjectId() : null);

        List<UserTasks> assignments = userTaskRepository.findByTasksTaskId(task.getTaskId());
        if (assignments == null || assignments.isEmpty()) {
            dto.setAssignedUserIds(Collections.emptyList());
        } else {
            dto.setAssignedUserIds(assignments.stream()
                    .map(UserTasks::getUsers)
                    .map(Users::getUserId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private CommentResponseDTO toCommentResponse(Feedback feedback) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setFeedbackId(feedback.getFeedbackId());
        dto.setTaskId(feedback.getTask() != null ? feedback.getTask().getTaskId() : null);
        dto.setUserId(feedback.getPostedUser() != null ? feedback.getPostedUser().getUserId() : null);
        dto.setCommentText(feedback.getCommentText());
        dto.setPostTime(feedback.getPostTime());
        return dto;
    }

    private AttachmentResponseDTO toAttachmentResponse(TaskAttachment attachment) {
        AttachmentResponseDTO dto = new AttachmentResponseDTO();
        dto.setAttachmentId(attachment.getAttachmentId());
        dto.setTaskId(attachment.getUserTask() != null && attachment.getUserTask().getTasks() != null
                ? attachment.getUserTask().getTasks().getTaskId()
                : null);
        dto.setUserId(attachment.getUserTask() != null && attachment.getUserTask().getUsers() != null
                ? attachment.getUserTask().getUsers().getUserId()
                : null);
        dto.setFileName(attachment.getFileName());
        dto.setFileUrl(attachment.getFileUrl());
        dto.setUploadedAt(attachment.getUploadedAt());
        return dto;
    }
}
