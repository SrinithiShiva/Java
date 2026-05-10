package com.example.codeRank.repository;

import com.example.codeRank.model.CodeSnippet;
import com.example.codeRank.model.Language;
import com.example.codeRank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, Long> {
    List<CodeSnippet> findByUserOrderByCreatedAtDesc(User user);
    List<CodeSnippet> findByUserAndLanguageOrderByCreatedAtDesc(User user, Language language);
    Optional<CodeSnippet> findByIdAndUser(Long id, User user);
}

