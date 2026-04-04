package com.example.TaskManagement.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long projectId;
    private String projectName;
    private String projectDescription;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tasks> projectTasks;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public List<Tasks> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<Tasks> projectTasks) {
        this.projectTasks = projectTasks;
    }
}
