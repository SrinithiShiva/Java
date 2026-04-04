package com.example.sharerecipe.service;

import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.entity.ChefFollow;
import com.example.sharerecipe.repository.ChefFollowRepository;
import com.example.sharerecipe.repository.ChefRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {
	private final ChefFollowRepository chefFollowRepository;
	private final ChefRepository chefRepository;

	public FollowService(ChefFollowRepository chefFollowRepository, ChefRepository chefRepository) {
		this.chefFollowRepository = chefFollowRepository;
		this.chefRepository = chefRepository;
	}

	@Transactional
	public void follow(UUID followerId, UUID followeeId) {
		if (followerId.equals(followeeId)) {
			throw new IllegalArgumentException("Cannot follow yourself");
		}
		if (chefFollowRepository.findByFollowerIdAndFolloweeId(followerId, followeeId).isPresent()) {
			return;
		}
		Chef follower = chefRepository.findById(followerId)
				.orElseThrow(() -> new IllegalArgumentException("Follower not found"));
		Chef followee = chefRepository.findById(followeeId)
				.orElseThrow(() -> new IllegalArgumentException("Followee not found"));
		ChefFollow follow = new ChefFollow();
		follow.setFollower(follower);
		follow.setFollowee(followee);
		chefFollowRepository.save(follow);
	}

	@Transactional
	public void unfollow(UUID followerId, UUID followeeId) {
		chefFollowRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
				.ifPresent(chefFollowRepository::delete);
	}

	@Transactional(readOnly = true)
	public List<Chef> listFollowedChefs(UUID followerId) {
		return chefFollowRepository.findByFollowerId(followerId).stream()
				.map(ChefFollow::getFollowee)
				.toList();
	}
}
