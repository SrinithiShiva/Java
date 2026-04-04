package com.example.TaskManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TaskManagement.dto.ProjectCreateDTO;
import com.example.TaskManagement.dto.ProjectResponseDTO;
import com.example.TaskManagement.entity.Project;
import com.example.TaskManagement.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectCreateDTO createDTO) {
        Project project = projectService.createProject(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(toProjectResponse(project));
    }

    private ProjectResponseDTO toProjectResponse(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setProjectId(project.getProjectId());
        dto.setProjectName(project.getProjectName());
        dto.setProjectDescription(project.getProjectDescription());
        return dto;
    }
}
