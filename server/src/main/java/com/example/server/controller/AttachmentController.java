package com.example.server.controller;


import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.attachment.Attachment;
import com.example.server.service.AchievementService;
import com.example.server.service.AttachmentService;
import com.example.server.service.UserKeyService;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    private final UserKeyService userKeyService;
    private final AchievementService achievementService;

    public AttachmentController(AttachmentService attachmentService, UserKeyService userKeyService, AchievementService achievementService) {
        this.attachmentService = attachmentService;
        this.userKeyService = userKeyService;
        this.achievementService = achievementService;
    }

    @GetMapping
    public List<Attachment> getAttachments(@RequestParam Long id, @RequestHeader("x-api-key") String key) throws IOException, AuthException {
        Achievement achievement = achievementService.getAchievement(id);
        userKeyService.checkAuthAchievement(achievement, key);

        return attachmentService.getAttachments(id);
    }

    @PostMapping
    public void createAttachment(@RequestBody Attachment attachment, @RequestHeader("x-api-key") String key) throws IOException, AuthException {
        userKeyService.checkAuthAttachment(attachment, key);

        attachmentService.createAttachment(attachment);
    }

    @DeleteMapping
    public void deleteAttachment(@RequestParam Long id, @RequestHeader("x-api-key") String key) throws IOException, AuthException {
        userKeyService.checkAuthAttachment(attachmentService.getAttachmentById(id), key);

        attachmentService.deleteAttachment(id);
    }

    @PutMapping
    public void updateAttachment(@RequestBody Attachment changes, @RequestHeader("x-api-key") String key) throws AuthException {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id attachment при обновлении");
        }

        userKeyService.checkAuthAttachment(attachmentService.getAttachmentById(changes.getId()), key);

        attachmentService.updateAttachment(changes);
    }


}

// TODO: надо в response возвращать id созданного изображения