package com.example.sharerecipe.service;

import com.example.sharerecipe.entity.Recipe;
import com.example.sharerecipe.entity.RecipeImage;
import com.example.sharerecipe.exception.BadRequestException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
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
				throw new BadRequestException("Unsupported image: " + file.getOriginalFilename());
			}

			String extension = getExtension(file.getOriginalFilename());
			String baseName = UUID.randomUUID().toString();
			Path recipeDir = uploadDir.resolve("recipes").resolve(recipe.getId().toString());
			Files.createDirectories(recipeDir);

			Path originalPath = recipeDir.resolve(baseName + "." + extension);
			Path thumbPath = recipeDir.resolve(baseName + "_thumb." + extension);

			BufferedImage resized = Thumbnails.of(original)
					.size(maxWidth, maxWidth)
					.keepAspectRatio(true)
					.asBufferedImage();
			ImageIO.write(resized, extension, originalPath.toFile());

			BufferedImage thumb = Thumbnails.of(original)
					.size(thumbWidth, thumbWidth)
					.keepAspectRatio(true)
					.asBufferedImage();
			ImageIO.write(thumb, extension, thumbPath.toFile());

			RecipeImage image = new RecipeImage();
			image.setRecipe(recipe);
			image.setPath(relativePath(originalPath));
			image.setThumbnailPath(relativePath(thumbPath));
			image.setWidth(resized.getWidth());
			image.setHeight(resized.getHeight());
			return image;
		} catch (IOException ex) {
			logger.error("Image upload failed for recipe {}", recipe.getId(), ex);
			throw new BadRequestException("Image upload failed");
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
