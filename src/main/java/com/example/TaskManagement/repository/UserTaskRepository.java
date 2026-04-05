package com.example.TaskManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TaskManagement.entity.UserTasks;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTasks, Long> {
    List<UserTasks> findByUsersUserId(Long userId);

    List<UserTasks> findByTasksTaskId(Long taskId);

    Optional<UserTasks> findByUsersUserIdAndTasksTaskId(Long userId, Long taskId);

    boolean existsByUsersUserIdAndTasksTaskId(Long userId, Long taskId);
}
