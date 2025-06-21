package com.example.server.service;

import com.example.server.controller.AchievementController.ImagesDTO;
import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.achievement.AchievementRepository;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;

    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public List<Achievement> getAchievementsByUserId(Long user_id) throws IOException {
        List<Achievement> achievements = achievementRepository.findAllByUserId(user_id);

        for (Achievement achievement : achievements) {
            String path2images = System.getProperty("user.dir") + "/" +
                                            achievement.getUser_id() + "/" +
                                            achievement.getContest_id();
            File folder = new File(path2images);
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

                ImageIO.write(image, "JPEG", file);

                achievement.addImage(image);
            }

        }

        return achievements;
    }

    public void createAchievements(List<Achievement> achievements) {
        achievementRepository.saveAll(achievements);
    }

    public void deleteAchievementsByIds(List<Long> ids) {
        for (Long id : ids) {
            getAchievement(id);
            achievementRepository.deleteById(id);
        }
    }

    public Achievement getAchievement(Long id) {
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()) {
            throw new IllegalStateException("achievement с id: " + id + " не существует");
        }
        return optionalAchievement.get();
    }

    public void removeImagesFromAchievements(List<ImagesDTO> ids) {
        List<Achievement> achievements = new ArrayList<>();

        for (ImagesDTO imagesDTO : ids) {
            Achievement achievement = getAchievement(imagesDTO.getAchievement_id());
            achievement.removeImageIds(imagesDTO.getImage_ids());
            achievements.add(achievement);
        }
        achievementRepository.saveAll(achievements);
    }
}
