package com.example.sharerecipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chef_follows", uniqueConstraints = {
		@UniqueConstraint(name = "uk_chef_follow", columnNames = {"follower_id", "followee_id"})
})
public class ChefFollow {
	@Id
	@Column(nullable = false, updatable = false)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_id", nullable = false)
	private Chef follower;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "followee_id", nullable = false)
	private Chef followee;

	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	void onCreate() {
		if (id == null) {
			id = UUID.randomUUID();
		}
		createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public Chef getFollower() {
		return follower;
	}

	public void setFollower(Chef follower) {
		this.follower = follower;
	}

	public Chef getFollowee() {
		return followee;
	}

	public void setFollowee(Chef followee) {
		this.followee = followee;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
