package com.example.TaskManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TaskManagement.entity.UserTeam;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
}
