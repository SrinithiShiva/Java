package com.example.sharerecipe.service;

import com.example.sharerecipe.entity.Recipe;
import com.example.sharerecipe.entity.RecipeImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	private final Path uploadDir;
	private final int maxWidth;
	private final int thumbWidth;

	public ImageService(@Value("${app.upload.dir:uploads}") String uploadDir,
			@Value("${app.upload.max-width:1280}") int maxWidth,
			@Value("${app.upload.thumb-width:420}") int thumbWidth) {
		this.uploadDir = Paths.get(uploadDir);
		this.maxWidth = maxWidth;
		this.thumbWidth = thumbWidth;
	}

	public RecipeImage storeImage(Recipe recipe, MultipartFile file) {
		try (InputStream input = file.getInputStream()) {
			BufferedImage original = ImageIO.read(input);
			if (original == null) {
				throw new IllegalArgumentException("Unsupported image");
			}

			String extension = getExtension(file.getOriginalFilename());
			String baseName = UUID.randomUUID().toString();
			Path recipeDir = uploadDir.resolve("recipes").resolve(recipe.getId().toString());
			Files.createDirectories(recipeDir);

			Path originalPath = recipeDir.resolve(baseName + "." + extension);
			Path thumbPath = recipeDir.resolve(baseName + "_thumb." + extension);

			Thumbnails.of(original)
					.size(maxWidth, maxWidth)
					.keepAspectRatio(true)
					.toFile(originalPath.toFile());

			Thumbnails.of(original)
					.size(thumbWidth, thumbWidth)
					.keepAspectRatio(true)
					.toFile(thumbPath.toFile());

			RecipeImage image = new RecipeImage();
			image.setRecipe(recipe);
			image.setPath(relativePath(originalPath));
			image.setThumbnailPath(relativePath(thumbPath));
			image.setWidth(original.getWidth());
			image.setHeight(original.getHeight());
			return image;
		} catch (IOException ex) {
			throw new IllegalArgumentException("Image upload failed");
		}
	}

	private String getExtension(String originalName) {
		if (originalName == null) {
			return "jpg";
		}
		int dot = originalName.lastIndexOf('.');
		if (dot <= 0 || dot == originalName.length() - 1) {
			return "jpg";
		}
		return originalName.substring(dot + 1).toLowerCase(Locale.ROOT);
	}

	private String relativePath(Path path) {
		Path normalized = uploadDir.toAbsolutePath().normalize();
		Path absolute = path.toAbsolutePath().normalize();
		return normalized.relativize(absolute).toString().replace('\\', '/');
	}
}
