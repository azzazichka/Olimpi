package com.example.server.controller;

import com.example.server.repository.achievement.Achievement;
import com.example.server.service.AchievementService;
import com.example.server.service.AchievementService.AchievementDTO;
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
    public List<AchievementDTO> getAchievement(@RequestParam Long user_id) throws IOException {
        return achievementService.getAchievementsByUserId(user_id);
    }

    @PostMapping
    public void createAchievement(@RequestBody Achievement achievement) {
        achievementService.createAchievement(achievement);
    }

    @DeleteMapping
    public void deleteAchievement(@RequestParam Long achievement_id) {
        achievementService.deleteAchievementById(achievement_id);
    }


    @PutMapping(path = "/add_image")
    public void uploadImage2Achievement(
            @RequestParam Long achievement_id,
            @RequestParam MultipartFile image
    ) throws IOException {
        achievementService.uploadImage2Achievement(image, achievement_id);

    }

    @PutMapping(path = "/remove_image")
    public void removeImageFromAchievement(@RequestParam Long image_id, @RequestParam Long achievement_id) {
        achievementService.removeImageFromAchievement(image_id, achievement_id);
    }

}
