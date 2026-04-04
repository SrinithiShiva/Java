package com.example.sharerecipe.repository;

import com.example.sharerecipe.entity.PublishQueueItem;
import com.example.sharerecipe.entity.QueueStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishQueueRepository extends JpaRepository<PublishQueueItem, UUID> {
	List<PublishQueueItem> findTop10ByStatusOrderByCreatedAtAsc(QueueStatus status);
}
