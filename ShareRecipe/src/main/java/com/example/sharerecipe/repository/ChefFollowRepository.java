package com.example.sharerecipe.repository;

import com.example.sharerecipe.entity.ChefFollow;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefFollowRepository extends JpaRepository<ChefFollow, UUID> {
	boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
	Optional<ChefFollow> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
	List<ChefFollow> findByFollowerId(UUID followerId);
}
