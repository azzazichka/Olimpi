package com.example.server.controller;

import com.example.server.repository.achievement.Achievement;
import com.example.server.service.AchievementService;
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
    public List<Achievement> getAchievement(@RequestParam Long user_id) throws IOException {
        return achievementService.getAchievementsByUserId(user_id);
    }

    @PostMapping
    public void createAchievement(@RequestBody Achievement achievement) throws IOException {
        achievementService.createAchievement(achievement);
    }

    @DeleteMapping
    public void deleteAchievement(@RequestParam Long id) throws IOException {
        achievementService.deleteAchievementById(id);
    }
}
