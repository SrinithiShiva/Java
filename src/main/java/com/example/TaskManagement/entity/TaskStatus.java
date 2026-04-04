package com.example.TaskManagement.entity;

public enum TaskStatus {
    OPEN("Open"),
    COMPLETED("Completed");

    private String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

