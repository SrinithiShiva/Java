package com.example.TaskManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.TaskManagement.dto.AttachmentCreateDTO;
import com.example.TaskManagement.dto.AttachmentResponseDTO;
import com.example.TaskManagement.dto.CommentCreateDTO;
import com.example.TaskManagement.dto.CommentResponseDTO;
import com.example.TaskManagement.dto.TaskAssignDTO;
import com.example.TaskManagement.dto.TaskCreateDTO;
import com.example.TaskManagement.dto.TaskResponseDTO;
import com.example.TaskManagement.dto.TaskStatusUpdateDTO;
import com.example.TaskManagement.entity.TaskStatus;
import com.example.TaskManagement.service.TaskService;

@RestController
@RequestMapping("/tasks")
@PreAuthorize("isAuthenticated()")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskCreateDTO createDTO) {
        TaskResponseDTO response = taskService.createTask(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksAssignedToUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksAssignedToUser(userId));
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable Long taskId,
            @RequestBody TaskStatusUpdateDTO updateDTO) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, updateDTO.getTaskStatus()));
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<TaskResponseDTO> assignTask(@PathVariable Long taskId, @RequestBody TaskAssignDTO assignDTO) {
        return ResponseEntity.ok(taskService.assignTask(taskId, assignDTO.getUserId()));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponseDTO>> filterTasks(@RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.filterTasksByStatus(status));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchTasks(@RequestParam String query) {
        return ResponseEntity.ok(taskService.searchTasks(query));
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long taskId, @RequestBody CommentCreateDTO commentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addComment(taskId, commentDTO));
    }

    @PostMapping("/{taskId}/attachments")
    public ResponseEntity<AttachmentResponseDTO> addAttachment(@PathVariable Long taskId, @RequestBody AttachmentCreateDTO attachmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addAttachment(taskId, attachmentDTO));
    }
}
