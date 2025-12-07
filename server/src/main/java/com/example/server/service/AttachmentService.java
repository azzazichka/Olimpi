package com.example.server.service;

import com.example.server.repository.achievement.Achievement;
import com.example.server.repository.attachment.Attachment;
import com.example.server.repository.attachment.AttachmentRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public List<Attachment> getAttachments(Long achievementId) throws IOException {
        List<Attachment> attachments = attachmentRepository.findAllByAchievementId(achievementId);

        for (Attachment attachment : attachments) {
            byte[] imageBytes = Files.readAllBytes(Path.of(attachment.getPath()));
            String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);
            attachment.setImageBytesBase64(imageBytesBase64);
        }

        return attachments;
    }

    public Attachment getAttachmentById(Long id) {
        Optional<Attachment> attachment = attachmentRepository.findById(id);
        if (attachment.isEmpty()) {
            throw new IllegalArgumentException("Медиа с id " + id + " не существует");
        }
        return attachment.get();
    }

    public void deleteAttachment(Long id) throws IOException {
        Attachment attachment = getAttachmentById(id);

        File img = new File(attachment.getPath());

        boolean deleted = img.delete();
        if (!deleted) {
            throw new IOException("медиа не удалено: " + attachment);
        }

        attachmentRepository.deleteById(id);
    }

    public Long createAttachment(Attachment attachment) throws IOException {
        if (attachment.getImageBytesBase64() == null) {
            throw new IllegalArgumentException("Не получено медиа " + attachment.toString());
        }
        attachmentRepository.save(attachment);
        byte[] imageBytes = Base64.getDecoder().decode(attachment.getImageBytesBase64());
        Files.write(Path.of(attachment.getPath()), imageBytes);
        return attachment.getId();
    }

    public void updateAttachment(Attachment changes) {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id attachment при обновлении");
        }

        Attachment newAttachment = getAttachmentById(changes.getId());
        if (changes.getTitle() != null) {
            newAttachment.setTitle(changes.getTitle());
        }

        attachmentRepository.save(newAttachment);
    }

    public static String getPath2Attachments(Long achievement_id) {
        return System.getProperty("user.dir") + "/src/main/attachments/achievement_" + achievement_id;
    }
}
