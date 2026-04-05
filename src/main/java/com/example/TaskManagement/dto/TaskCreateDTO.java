package com.example.TaskManagement.dto;

import java.util.Date;
import java.util.List;

public class TaskCreateDTO {
    private String taskName;
    private String taskDescription;
    private Date dueDate;
    private Long projectId;
    private List<Long> assignedUserIds;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<Long> getAssignedUserIds() {
        return assignedUserIds;
    }

    public void setAssignedUserIds(List<Long> assignedUserIds) {
        this.assignedUserIds = assignedUserIds;
    }
}
