package com.example.sharerecipe.worker;

import com.example.sharerecipe.entity.PublishQueueItem;
import com.example.sharerecipe.entity.QueueStatus;
import com.example.sharerecipe.entity.Recipe;
import com.example.sharerecipe.entity.RecipeStatus;
import com.example.sharerecipe.repository.PublishQueueRepository;
import com.example.sharerecipe.repository.RecipeRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Profile;

@Component
@Profile("worker")
public class PublishQueueWorker {
	private final PublishQueueRepository publishQueueRepository;
	private final RecipeRepository recipeRepository;

	public PublishQueueWorker(PublishQueueRepository publishQueueRepository, RecipeRepository recipeRepository) {
		this.publishQueueRepository = publishQueueRepository;
		this.recipeRepository = recipeRepository;
	}

	@Scheduled(fixedDelayString = "${app.worker.poll-interval-ms:5000}")
	@Transactional
	public void processQueue() {
		List<PublishQueueItem> items = publishQueueRepository.findTop10ByStatusOrderByCreatedAtAsc(QueueStatus.PENDING);
		for (PublishQueueItem item : items) {
			try {
				item.setStatus(QueueStatus.PROCESSING);
				publishQueueRepository.save(item);
				Recipe recipe = recipeRepository.findById(item.getRecipeId())
						.orElseThrow(() -> new IllegalArgumentException("Recipe missing"));
				if (recipe.getStatus() != RecipeStatus.PUBLISHED) {
					recipe.setStatus(RecipeStatus.PUBLISHED);
					recipe.setPublishedAt(Instant.now());
					recipeRepository.save(recipe);
				}
				item.setStatus(QueueStatus.DONE);
				item.setError(null);
			} catch (Exception ex) {
				item.setStatus(QueueStatus.FAILED);
				item.setError(ex.getMessage());
			}
		}
	}
}
