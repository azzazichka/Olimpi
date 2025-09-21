package com.example.server.repository.attachment;

import com.example.server.repository.achievement.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query(value = "SELECT * FROM attachments WHERE achievement_id = :achievementId", nativeQuery = true)
    List<Attachment> findAllByAchievementId(Long achievementId);
}
