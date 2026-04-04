package com.example.sharerecipe.service;

import com.example.sharerecipe.dto.PageResponse;
import com.example.sharerecipe.dto.PaginationMeta;
import com.example.sharerecipe.dto.RecipeCreateRequest;
import com.example.sharerecipe.dto.RecipeImageResponse;
import com.example.sharerecipe.dto.RecipeResponse;
import com.example.sharerecipe.dto.RecipeUpdateRequest;
import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.entity.PublishQueueItem;
import com.example.sharerecipe.entity.QueueStatus;
import com.example.sharerecipe.entity.Recipe;
import com.example.sharerecipe.entity.RecipeImage;
import com.example.sharerecipe.entity.RecipeStatus;
import com.example.sharerecipe.repository.PublishQueueRepository;
import com.example.sharerecipe.repository.RecipeRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RecipeService {
	private final RecipeRepository recipeRepository;
	private final PublishQueueRepository publishQueueRepository;
	private final ImageService imageService;
	private final String uploadUrlPrefix;

	public RecipeService(RecipeRepository recipeRepository,
			PublishQueueRepository publishQueueRepository,
			ImageService imageService,
			@Value("${app.upload.url-prefix:/uploads/}") String uploadUrlPrefix) {
		this.recipeRepository = recipeRepository;
		this.publishQueueRepository = publishQueueRepository;
		this.imageService = imageService;
		this.uploadUrlPrefix = uploadUrlPrefix.endsWith("/") ? uploadUrlPrefix : uploadUrlPrefix + "/";
	}

	@Transactional
	public RecipeResponse createRecipe(Chef chef, RecipeCreateRequest request, List<MultipartFile> images) {
		Recipe recipe = new Recipe();
		recipe.setAuthor(chef);
		applyRecipeData(recipe, request.getTitle(), request.getSummary(), request.getIngredients(),
				request.getSteps(), request.getLabels());
		if (!request.isDraft()) {
			recipe.setStatus(RecipeStatus.QUEUED);
		}
		Recipe saved = recipeRepository.save(recipe);
		if (images != null) {
			for (MultipartFile file : images) {
				RecipeImage image = imageService.storeImage(saved, file);
				saved.getImages().add(image);
			}
		}
		if (!request.isDraft()) {
			queuePublish(saved);
		}
		return toResponse(saved);
	}

	@Transactional
	public RecipeResponse updateRecipe(UUID recipeId, Chef chef, RecipeUpdateRequest request, boolean admin) {
		Recipe recipe = requireRecipe(recipeId);
		authorizeOwner(chef, recipe, admin);
		applyRecipeData(recipe, request.getTitle(), request.getSummary(), request.getIngredients(),
				request.getSteps(), request.getLabels());
		return toResponse(recipeRepository.save(recipe));
	}

	@Transactional
	public RecipeResponse addImages(UUID recipeId, Chef chef, List<MultipartFile> images, boolean admin) {
		Recipe recipe = requireRecipe(recipeId);
		authorizeOwner(chef, recipe, admin);
		if (images != null) {
			for (MultipartFile file : images) {
				RecipeImage image = imageService.storeImage(recipe, file);
				recipe.getImages().add(image);
			}
		}
		return toResponse(recipeRepository.save(recipe));
	}

	@Transactional
	public RecipeResponse publishRecipe(UUID recipeId, Chef chef, boolean admin) {
		Recipe recipe = requireRecipe(recipeId);
		authorizeOwner(chef, recipe, admin);
		recipe.setStatus(RecipeStatus.QUEUED);
		recipeRepository.save(recipe);
		queuePublish(recipe);
		return toResponse(recipe);
	}

	@Transactional
	public void deleteRecipe(UUID recipeId, Chef chef, boolean admin) {
		Recipe recipe = requireRecipe(recipeId);
		if (!admin) {
			authorizeOwner(chef, recipe, admin);
		}
		recipeRepository.delete(recipe);
	}

	public PageResponse<RecipeResponse> listPublishedRecipes(String keyword,
			Instant publishedFrom,
			Instant publishedTo,
			UUID chefId,
			String chefHandle,
			Integer page,
			Integer pageSize) {
		Pageable pageable = pageRequest(page, pageSize);
		Specification<Recipe> spec = Specification.where(com.example.sharerecipe.service.RecipeSpecifications.publishedOnly())
				.and(RecipeSpecifications.keyword(keyword))
				.and(RecipeSpecifications.publishedFrom(publishedFrom))
				.and(RecipeSpecifications.publishedTo(publishedTo))
				.and(RecipeSpecifications.chefId(chefId))
				.and(RecipeSpecifications.chefHandle(chefHandle));
		Page<Recipe> result = recipeRepository.findAll(spec, pageable);
		return toPageResponse(result);
	}

	public PageResponse<RecipeResponse> listFollowedRecipes(List<Chef> followees,
			String keyword,
			Instant publishedFrom,
			Instant publishedTo,
			Integer page,
			Integer pageSize) {
		Pageable pageable = pageRequest(page, pageSize);
		Specification<Recipe> spec = Specification.where(RecipeSpecifications.publishedOnly())
				.and(RecipeSpecifications.authorIn(followees))
				.and(RecipeSpecifications.keyword(keyword))
				.and(RecipeSpecifications.publishedFrom(publishedFrom))
				.and(RecipeSpecifications.publishedTo(publishedTo));
		Page<Recipe> result = recipeRepository.findAll(spec, pageable);
		return toPageResponse(result);
	}

	private void applyRecipeData(Recipe recipe, String title, String summary, List<String> ingredients,
			List<String> steps, List<String> labels) {
		recipe.setTitle(title);
		recipe.setSummary(summary);
		List<String> safeIngredients = ingredients == null ? List.of() : ingredients;
		List<String> safeSteps = steps == null ? List.of() : steps;
		List<String> safeLabels = labels == null ? List.of() : labels;
		recipe.setIngredients(new java.util.ArrayList<>(safeIngredients));
		recipe.setSteps(new java.util.ArrayList<>(safeSteps));
		recipe.setLabels(new java.util.ArrayList<>(safeLabels));
		recipe.setIngredientsText(String.join(" ", safeIngredients));
		recipe.setStepsText(String.join(" ", safeSteps));
	}

	private void queuePublish(Recipe recipe) {
		PublishQueueItem item = new PublishQueueItem();
		item.setRecipeId(recipe.getId());
		item.setStatus(QueueStatus.PENDING);
		publishQueueRepository.save(item);
	}

	private Recipe requireRecipe(UUID recipeId) {
		return recipeRepository.findById(recipeId)
				.orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
	}

	private void authorizeOwner(Chef chef, Recipe recipe, boolean admin) {
		if (!admin && !recipe.getAuthor().getId().equals(chef.getId())) {
			throw new IllegalArgumentException("Forbidden");
		}
	}

	private PageResponse<RecipeResponse> toPageResponse(Page<Recipe> result) {
		List<RecipeResponse> data = result.getContent().stream().map(this::toResponse).toList();
		PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(),
				result.getTotalElements(), result.getTotalPages());
		return new PageResponse<>(data, meta);
	}

	private RecipeResponse toResponse(Recipe recipe) {
		RecipeResponse response = new RecipeResponse();
		response.setId(recipe.getId());
		response.setTitle(recipe.getTitle());
		response.setSummary(recipe.getSummary());
		response.setIngredients(recipe.getIngredients());
		response.setSteps(recipe.getSteps());
		response.setLabels(recipe.getLabels());
		response.setStatus(recipe.getStatus().name());
		response.setPublishedAt(recipe.getPublishedAt());
		response.setCreatedAt(recipe.getCreatedAt());
		response.setUpdatedAt(recipe.getUpdatedAt());
		response.setChefId(recipe.getAuthor().getId());
		response.setChefHandle(recipe.getAuthor().getHandle());
		response.setImages(recipe.getImages().stream().map(this::toImageResponse).toList());
		return response;
	}

	private RecipeImageResponse toImageResponse(RecipeImage image) {
		RecipeImageResponse response = new RecipeImageResponse();
		response.setId(image.getId());
		response.setUrl(uploadUrlPrefix + image.getPath());
		response.setThumbnailUrl(uploadUrlPrefix + image.getThumbnailPath());
		response.setWidth(image.getWidth());
		response.setHeight(image.getHeight());
		return response;
	}

	private Pageable pageRequest(Integer page, Integer pageSize) {
		int resolvedPage = pageOrDefault(page);
		int resolvedSize = pageSizeOrDefault(pageSize);
		return PageRequest.of(resolvedPage, resolvedSize, Sort.by(Sort.Direction.DESC, "publishedAt", "createdAt"));
	}

	private int pageOrDefault(Integer page) {
		return page == null || page < 0 ? 0 : page;
	}

	private int pageSizeOrDefault(Integer pageSize) {
		int defaultSize = 20;
		int maxSize = 100;
		if (pageSize == null || pageSize <= 0) {
			return defaultSize;
		}
		return Math.min(pageSize, maxSize);
	}
}
