package com.example.sharerecipe.repository;

import com.example.sharerecipe.entity.RecipeImage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, UUID> {
}
