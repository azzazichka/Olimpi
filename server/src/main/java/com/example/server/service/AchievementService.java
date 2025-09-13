package com.example.server.service;

import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.achievement.AchievementRepository;
import lombok.Data;
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

            String path2images = getPath2images(achievement.getId());
            File folder = new File(path2images);
            for (File img : Objects.requireNonNull(folder.listFiles())) {
                achievementDTO.image_ids.add(Integer.valueOf(img.getName()));
                achievementDTO.image_bytes.add(Files.readAllBytes(img.toPath()));
            }

            achievementDTOs.add(achievementDTO);
        }

        return achievementDTOs;
    }

    public void createAchievement(Achievement achievement) {
        achievementRepository.save(achievement);

        File file = new File(getPath2images(achievement.getId()));
        if (!file.mkdir()) {
            System.out.println("Директория " + getPath2images(achievement.getId()) + " не создана");
        }
    }

    public void deleteAchievementById(Long id) {
        Achievement achievement = getAchievement(id);

        String path = getPath2images(achievement.getId());
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
        achievementRepository.deleteById(id);
    }

    public Achievement getAchievement(Long id) {
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()) {
            throw new IllegalStateException("achievement с id: " + id + " не существует");
        }
        return optionalAchievement.get();
    }

    public void removeImageFromAchievement(Long image_id, Long achievement_id) {
        Achievement achievement = getAchievement(achievement_id);

        String path2images = getPath2images(achievement.getId());

        File img = new File(path2images + "\\" + image_id.toString());

        boolean deleted = img.delete();
        if (!deleted) {
            System.out.println("изображение не удалено, achievement id:" + achievement.getId() + " image_id: " + image_id);
        }
    }

    public static String getPath2images(Long achievement_id) {
        return System.getProperty("user.dir") + "\\src\\main\\java\\upload_images\\achievement_" + achievement_id;
    }

    public void uploadImage2Achievement(MultipartFile image, Long achievement_id) throws IOException {
        Achievement achievement = getAchievement(achievement_id);
        Files.write(Path.of(getPath2images(achievement.getId()) + "\\" + achievement.getImage_ids_sequence()), image.getBytes());
        achievement.incrementSequence();
        achievementRepository.save(achievement);
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
