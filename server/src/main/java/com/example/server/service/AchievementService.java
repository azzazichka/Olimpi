package com.example.server.service;

import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.achievement.AchievementRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.server.service.AttachmentService.getPath2Attachments;

@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AttachmentService attachmentService;

    public AchievementService(
            AchievementRepository achievementRepository, 
            AttachmentService attachmentService
    ) {
        this.achievementRepository = achievementRepository;
        this.attachmentService = attachmentService;
    }

    public List<Achievement> getAchievements(Long user_id) {
        return achievementRepository.findAllByUserId(user_id);
    }

    public void createAchievement(Achievement achievement) throws IOException {
        achievementRepository.save(achievement);

        File file = new File(getPath2Attachments(achievement.getId()));
        if (!file.mkdir()) {
            throw new IOException("Директория " + getPath2Attachments(achievement.getId()) + " не создана");
        }
    }

    public void deleteAchievementById(Long id) throws IOException {
        File file = new File(getPath2Attachments(id));
        for (File img : Objects.requireNonNull(file.listFiles())) {
            attachmentService.deleteAttachment(Long.valueOf(img.getName()));
        }
        boolean deleted = file.delete();
        if (!deleted) {
            throw new IOException("файл: " + file.getName() + " не удалён");
        }

        achievementRepository.deleteById(id);
    }

    public Achievement getAchievement(Long id) {
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()) {
            throw new IllegalStateException("achievement с id " + id + " не существует");
        }
        return optionalAchievement.get();
    }
}
