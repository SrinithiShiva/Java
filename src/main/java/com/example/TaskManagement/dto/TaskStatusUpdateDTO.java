package com.example.TaskManagement.dto;

import com.example.TaskManagement.entity.TaskStatus;

public class TaskStatusUpdateDTO {
    private TaskStatus taskStatus;

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
