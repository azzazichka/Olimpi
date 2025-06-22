package com.example.server.service;

import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.achievement.AchievementRepository;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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



    public List<AchievementDTO> getAchievementsByUserId(Long user_id) throws IOException {
        List<AchievementDTO> achievementDTOs = new ArrayList<>();
        List<Achievement> achievements = achievementRepository.findAllByUserId(user_id);

        for (Achievement achievement : achievements) {
            AchievementDTO achievementDTO = new AchievementDTO();
            achievementDTO.achievement = achievement;

            String path2images = getPath2images(achievement);
            File folder = new File(path2images);
            for (File img : Objects.requireNonNull(folder.listFiles())) {
                achievementDTO.image_ids.add(Integer.valueOf(img.getName()));
                achievementDTO.image_bytes.add(Files.readAllBytes(img.toPath()));
            }

            achievementDTOs.add(achievementDTO);
        }

        return achievementDTOs;
    }

    public void createAchievements(List<Achievement> achievements) {
        achievementRepository.saveAll(achievements);

        for (Achievement achievement : achievements) {
            File file = new File(getPath2images(achievement));
            if (!file.mkdir()) {
                System.out.println("Директория " + getPath2images(achievement) + " не создана");
            }
        }
    }

    public void deleteAchievementsByIds(List<Long> ids) {
        for (Long id : ids) {
            Achievement achievement = getAchievement(id);

            String path = getPath2images(achievement);
            File file = new File(path);
            for (File img : Objects.requireNonNull(file.listFiles())) {
                boolean deleted = img.delete();
                if (!deleted) {
                    System.out.println("изображение не удалено, achievement id:" + achievement.getId() + " image_id: " + img.getName());
                }
            }
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("файл: " + file.getName() + " не удалён");
            }
        }
        achievementRepository.deleteAllById(ids);
    }

    public Achievement getAchievement(Long id) {
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()) {
            throw new IllegalStateException("achievement с id: " + id + " не существует");
        }
        return optionalAchievement.get();
    }

    public void removeImagesFromAchievements(List<ImagesDTO> ids) {
        for (ImagesDTO imagesDTO : ids) {
            Achievement achievement = getAchievement(imagesDTO.achievement_id);

            String path2images = getPath2images(achievement);

            for (Long image_id : imagesDTO.image_ids) {
                File img = new File(path2images + "\\" + image_id.toString());

                boolean deleted = img.delete();
                if (!deleted) {
                    System.out.println("изображение не удалено, achievement id:" + achievement.getId() + " image_id: " + image_id);
                }
            }
        }
    }

    public static String getPath2images(Achievement achievement) {
        return System.getProperty("user.dir") + "\\src\\main\\java\\upload_images\\achievement_" + achievement.getId();
    }

    public void uploadImages2Achievements(List<MultipartFile> images, List<Long> achievement_ids) throws IOException {
        Long current_achievement_id = -1L;
        Achievement achievement = null;
        String imgs_path = "";

        for (int i = 0; i < achievement_ids.size(); i++) {
            if (achievement == null || !Objects.equals(achievement_ids.get(i), current_achievement_id)) {
                if (achievement != null) {
                    achievementRepository.save(achievement);
                }

                achievement = getAchievement(achievement_ids.get(i));
                current_achievement_id = achievement_ids.get(i);
                imgs_path = getPath2images(achievement);
            }

            MultipartFile image = images.get(i);
            Files.write(Path.of(imgs_path + "\\" + achievement.getImage_ids_sequence()), image.getBytes());
            achievement.incrementSequence();
        }

        if (achievement != null) achievementRepository.save(achievement);
    }

    @Data
    public static class ImagesDTO {
        private Long achievement_id;
        private List<Long> image_ids;
    }

    @Data
    public static class AchievementDTO {
        private Achievement achievement;
        private List<Integer> image_ids;
        private List<byte[]> image_bytes;

        public AchievementDTO() {
            image_ids = new ArrayList<>();
            image_bytes = new ArrayList<>();
        }

    }

}
