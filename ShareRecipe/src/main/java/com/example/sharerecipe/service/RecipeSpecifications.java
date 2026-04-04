package com.example.sharerecipe.service;

import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.entity.Recipe;
import com.example.sharerecipe.entity.RecipeStatus;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public final class RecipeSpecifications {
	private RecipeSpecifications() {
	}

	public static Specification<Recipe> publishedOnly() {
		return (root, query, cb) -> cb.equal(root.get("status"), RecipeStatus.PUBLISHED);
	}

	public static Specification<Recipe> keyword(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return Specification.where(null);
		}
		String like = "%" + keyword.toLowerCase() + "%";
		return (root, query, cb) -> cb.or(
				cb.like(cb.lower(root.get("title")), like),
				cb.like(cb.lower(root.get("summary")), like),
				cb.like(cb.lower(root.get("ingredientsText")), like),
				cb.like(cb.lower(root.get("stepsText")), like)
		);
	}

	public static Specification<Recipe> publishedFrom(Instant from) {
		if (from == null) {
			return Specification.where(null);
		}
		return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publishedAt"), from);
	}

	public static Specification<Recipe> publishedTo(Instant to) {
		if (to == null) {
			return Specification.where(null);
		}
		return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("publishedAt"), to);
	}

	public static Specification<Recipe> chefId(UUID chefId) {
		if (chefId == null) {
			return Specification.where(null);
		}
		return (root, query, cb) -> cb.equal(root.get("author").get("id"), chefId);
	}

	public static Specification<Recipe> chefHandle(String handle) {
		if (handle == null || handle.isBlank()) {
			return Specification.where(null);
		}
		return (root, query, cb) -> cb.equal(cb.lower(root.get("author").get("handle")), handle.toLowerCase());
	}

	public static Specification<Recipe> authorIn(List<Chef> chefs) {
		if (chefs == null || chefs.isEmpty()) {
			return (root, query, cb) -> cb.disjunction();
		}
		return (root, query, cb) -> root.get("author").in(chefs);
	}
}
