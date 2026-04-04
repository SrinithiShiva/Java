package com.example.sharerecipe.repository;

import com.example.sharerecipe.entity.Chef;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefRepository extends JpaRepository<Chef, UUID> {
	Optional<Chef> findByEmail(String email);
	Optional<Chef> findByHandle(String handle);
	boolean existsByEmail(String email);
	boolean existsByHandle(String handle);
}
