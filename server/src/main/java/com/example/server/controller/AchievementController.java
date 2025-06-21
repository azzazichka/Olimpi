package com.example.server.controller;

import com.example.server.repository.achievement.Achievement;
import com.example.server.service.AchievementService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/achievements")
public class AchievementController {
    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public List<Achievement> getAchievementsByUserId(@RequestParam Long user_id) throws IOException {
        return achievementService.getAchievementsByUserId(user_id);
    }

    @PostMapping
    public void createAchievements(@RequestBody List<Achievement> achievements) {
        achievementService.createAchievements(achievements);
    }

    @DeleteMapping
    public void deleteAchievements(@RequestBody List<Long> ids) {
        achievementService.deleteAchievementsByIds(ids);
    }

    @Data
    public static class ImagesDTO {
        private Long achievement_id;
        private List<Long> image_ids;
        private List<MultipartFile> images;
    }

    @PutMapping(path = "/add_images")
    public void addImages2Achievements(@RequestBody List<ImagesDTO> images) {


    }


    @PutMapping(path = "/remove_images")
    public void removeImagesFromAchievements(@RequestBody List<ImagesDTO> ids) {
        achievementService.removeImagesFromAchievements(ids);
    }

}
