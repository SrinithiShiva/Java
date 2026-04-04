package com.example.TaskManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TaskManagement.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
