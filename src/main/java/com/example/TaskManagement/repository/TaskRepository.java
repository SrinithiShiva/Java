package com.example.TaskManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TaskManagement.entity.TaskStatus;
import com.example.TaskManagement.entity.Tasks;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByTaskStatus(TaskStatus taskStatus);

    List<Tasks> findByTaskNameContainingIgnoreCaseOrTaskDescriptionContainingIgnoreCase(String taskName, String taskDescription);
}
