package com.example.TaskManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TaskManagement.dto.ProjectCreateDTO;
import com.example.TaskManagement.entity.Project;
import com.example.TaskManagement.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(ProjectCreateDTO createDTO) {
        Project project = new Project();
        project.setProjectName(createDTO.getProjectName());
        project.setProjectDescription(createDTO.getProjectDescription());
        return projectRepository.save(project);
    }
}
