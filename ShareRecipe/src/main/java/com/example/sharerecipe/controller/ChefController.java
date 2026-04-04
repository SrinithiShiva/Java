package com.example.sharerecipe.controller;

import com.example.sharerecipe.auth.CustomUserDetails;
import com.example.sharerecipe.auth.SecurityUtils;
import com.example.sharerecipe.service.FollowService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chefs")
public class ChefController {
	private final FollowService followService;

	public ChefController(FollowService followService) {
		this.followService = followService;
	}

	@PostMapping("/{id}/follow")
	public ResponseEntity<Void> follow(@PathVariable("id") UUID followeeId) {
		CustomUserDetails current = SecurityUtils.currentUser();
		followService.follow(current.getId(), followeeId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}/follow")
	public ResponseEntity<Void> unfollow(@PathVariable("id") UUID followeeId) {
		CustomUserDetails current = SecurityUtils.currentUser();
		followService.unfollow(current.getId(), followeeId);
		return ResponseEntity.noContent().build();
	}
}
