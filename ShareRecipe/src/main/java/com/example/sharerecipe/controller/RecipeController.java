package com.example.sharerecipe.controller;

import com.example.sharerecipe.auth.CustomUserDetails;
import com.example.sharerecipe.auth.SecurityUtils;
import com.example.sharerecipe.dto.PageResponse;
import com.example.sharerecipe.dto.RecipeCreateRequest;
import com.example.sharerecipe.dto.RecipeResponse;
import com.example.sharerecipe.dto.RecipeUpdateRequest;
import com.example.sharerecipe.entity.Chef;
import com.example.sharerecipe.repository.ChefRepository;
import com.example.sharerecipe.service.FollowService;
import com.example.sharerecipe.service.RecipeService;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	private final RecipeService recipeService;
	private final ChefRepository chefRepository;
	private final FollowService followService;

	public RecipeController(RecipeService recipeService, ChefRepository chefRepository, FollowService followService) {
		this.recipeService = recipeService;
		this.chefRepository = chefRepository;
		this.followService = followService;
	}

	@GetMapping
	public ResponseEntity<PageResponse<RecipeResponse>> listRecipes(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "published_from", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant publishedFrom,
			@RequestParam(value = "published_to", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant publishedTo,
			@RequestParam(value = "chef_id", required = false) UUID chefId,
			@RequestParam(value = "chef_handle", required = false) String chefHandle,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "page_size", required = false) Integer pageSize
	) {
		return ResponseEntity.ok(recipeService.listPublishedRecipes(
				keyword, publishedFrom, publishedTo, chefId, chefHandle, page, pageSize));
	}

	@GetMapping("/followed")
	public ResponseEntity<PageResponse<RecipeResponse>> listFollowedRecipes(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "published_from", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant publishedFrom,
			@RequestParam(value = "published_to", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant publishedTo,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "page_size", required = false) Integer pageSize
	) {
		CustomUserDetails current = SecurityUtils.currentUser();
		List<Chef> followees = followService.listFollowedChefs(current.getId());
		return ResponseEntity.ok(recipeService.listFollowedRecipes(
				followees, keyword, publishedFrom, publishedTo, page, pageSize));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('CHEF') or hasRole('ADMIN')")
	public ResponseEntity<RecipeResponse> createRecipe(
			@Valid @RequestPart("data") RecipeCreateRequest request,
			@RequestPart(value = "images", required = false) List<MultipartFile> images
	) {
		Chef chef = currentChef();
		return ResponseEntity.ok(recipeService.createRecipe(chef, request, images));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('CHEF') or hasRole('ADMIN')")
	public ResponseEntity<RecipeResponse> updateRecipe(
			@PathVariable("id") UUID id,
			@Valid @org.springframework.web.bind.annotation.RequestBody RecipeUpdateRequest request
	) {
		CustomUserDetails current = SecurityUtils.currentUser();
		Chef chef = currentChef();
		return ResponseEntity.ok(recipeService.updateRecipe(id, chef, request, isAdmin(current)));
	}

	@PostMapping("/{id}/publish")
	@PreAuthorize("hasRole('CHEF') or hasRole('ADMIN')")
	public ResponseEntity<RecipeResponse> publishRecipe(@PathVariable("id") UUID id) {
		CustomUserDetails current = SecurityUtils.currentUser();
		Chef chef = currentChef();
		return ResponseEntity.ok(recipeService.publishRecipe(id, chef, isAdmin(current)));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('CHEF') or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteRecipe(@PathVariable("id") UUID id) {
		CustomUserDetails current = SecurityUtils.currentUser();
		Chef chef = chefRepository.findById(current.getId())
				.orElseThrow(() -> new IllegalArgumentException("Chef not found"));
		recipeService.deleteRecipe(id, chef, isAdmin(current));
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasRole('CHEF') or hasRole('ADMIN')")
	public ResponseEntity<RecipeResponse> addImages(
			@PathVariable("id") UUID id,
			@RequestPart("images") List<MultipartFile> images
	) {
		CustomUserDetails current = SecurityUtils.currentUser();
		Chef chef = currentChef();
		return ResponseEntity.ok(recipeService.addImages(id, chef, images, isAdmin(current)));
	}

	private Chef currentChef() {
		CustomUserDetails current = SecurityUtils.currentUser();
		return chefRepository.findById(current.getId())
				.orElseThrow(() -> new IllegalArgumentException("Chef not found"));
	}

	private boolean isAdmin(CustomUserDetails current) {
		return current.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
	}
}
