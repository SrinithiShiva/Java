package com.example.sharerecipe.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeResponse {
	private UUID id;
	private String title;
	private String summary;
	private List<String> ingredients = new ArrayList<>();
	private List<String> steps = new ArrayList<>();
	private List<String> labels = new ArrayList<>();
	private String status;
	private Instant publishedAt;
	private Instant createdAt;
	private Instant updatedAt;
	private UUID chefId;
	private String chefHandle;
	private List<RecipeImageResponse> images = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Instant publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UUID getChefId() {
		return chefId;
	}

	public void setChefId(UUID chefId) {
		this.chefId = chefId;
	}

	public String getChefHandle() {
		return chefHandle;
	}

	public void setChefHandle(String chefHandle) {
		this.chefHandle = chefHandle;
	}

	public List<RecipeImageResponse> getImages() {
		return images;
	}

	public void setImages(List<RecipeImageResponse> images) {
		this.images = images;
	}
}
