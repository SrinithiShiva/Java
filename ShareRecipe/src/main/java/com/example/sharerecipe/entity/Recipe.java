package com.example.sharerecipe.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipes")
public class Recipe {
	@Id
	@Column(nullable = false, updatable = false)
	private UUID id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, length = 2000)
	private String summary;

	@ElementCollection
	@CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
	@Column(name = "ingredient", nullable = false, length = 1000)
	private List<String> ingredients = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
	@Column(name = "step", nullable = false, length = 2000)
	private List<String> steps = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "recipe_labels", joinColumns = @JoinColumn(name = "recipe_id"))
	@Column(name = "label", nullable = false)
	private List<String> labels = new ArrayList<>();

	@Column(nullable = false, length = 4000)
	private String ingredientsText;

	@Column(nullable = false, length = 8000)
	private String stepsText;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RecipeStatus status = RecipeStatus.DRAFT;

	@Column
	private Instant publishedAt;

	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@Column(nullable = false)
	private Instant updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chef_id", nullable = false)
	private Chef author;

	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RecipeImage> images = new ArrayList<>();

	@PrePersist
	void onCreate() {
		if (id == null) {
			id = UUID.randomUUID();
		}
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}

	public UUID getId() {
		return id;
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

	public String getIngredientsText() {
		return ingredientsText;
	}

	public void setIngredientsText(String ingredientsText) {
		this.ingredientsText = ingredientsText;
	}

	public String getStepsText() {
		return stepsText;
	}

	public void setStepsText(String stepsText) {
		this.stepsText = stepsText;
	}

	public RecipeStatus getStatus() {
		return status;
	}

	public void setStatus(RecipeStatus status) {
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

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public Chef getAuthor() {
		return author;
	}

	public void setAuthor(Chef author) {
		this.author = author;
	}

	public List<RecipeImage> getImages() {
		return images;
	}
}
