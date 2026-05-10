package com.example.codeRank.repository;

import com.example.codeRank.model.Execution;
import com.example.codeRank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, Long> {
    List<Execution> findByUserOrderByCreatedAtDesc(User user);
    Optional<Execution> findByIdAndUser(Long id, User user);
    Long countByUser(User user);
    Long countByUserAndSuccess(User user, Boolean success);

    @Query("SELECT e.language, COUNT(e) FROM Execution e WHERE e.user = :user GROUP BY e.language")
    List<Object[]> countByUserGroupByLanguage(User user);

    @Query("SELECT AVG(e.executionTimeMs) FROM Execution e WHERE e.user = :user AND e.success = true")
    Double getAverageExecutionTimeByUser(User user);
}

