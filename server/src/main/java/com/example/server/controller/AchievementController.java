package com.example.server.controller;

import com.example.server.repository.achievement.Achievement;
import com.example.server.service.AchievementService;
import com.example.server.service.AchievementService.AchievementDTO;
import com.example.server.service.AchievementService.ImagesDTO;
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
    public List<AchievementDTO> getAchievementsByUserId(@RequestParam Long user_id) throws IOException {
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


    @PutMapping(path = "/add_images")
    public void uploadImages2Achievements(
            @RequestParam List<Long> achievement_ids,
            @RequestParam List<MultipartFile> images
    ) throws IOException {
        achievementService.uploadImages2Achievements(images, achievement_ids);

    }

    @PutMapping(path = "/remove_images")
    public void removeImagesFromAchievements(@RequestBody List<ImagesDTO> ids) {
        achievementService.removeImagesFromAchievements(ids);
    }

}
