package com.example.server.controller;

import com.example.server.repository.achievement.Achievement;
import com.example.server.service.AchievementService;
import com.example.server.service.UserKeyService;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/achievements")
public class AchievementController {
    private final AchievementService achievementService;
    private final UserKeyService userKeyService;

    public AchievementController(AchievementService achievementService, UserKeyService userKeyService) {
        this.achievementService = achievementService;
        this.userKeyService = userKeyService;
    }

    @GetMapping
    public List<Achievement> getAchievements(@RequestHeader("x-api-key") String key) throws IOException {
        Long user_id = userKeyService.getUserIdByKey(key);
        return achievementService.getAchievements(user_id);
    }

    @PostMapping
        public void createAchievement(
            @RequestBody Achievement achievement,
            @RequestHeader("x-api-key") String key
    ) throws IOException, AuthException {
        userKeyService.checkAuthAchievement(achievement, key);

        achievementService.createAchievement(achievement);
    }

    @DeleteMapping
    public void deleteAchievement(
            @RequestParam Long contestId, @RequestHeader("x-api-key") String key
    ) throws IOException, AuthException {
        Long userId = userKeyService.getUserIdByKey(key);


        Long id = achievementService.getAchievementIdByUserIdAndContestId(userId, contestId);
        achievementService.deleteAchievementById(id);
    }

}
