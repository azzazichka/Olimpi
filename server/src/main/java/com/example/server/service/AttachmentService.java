package com.example.server.service;

import com.example.server.repository.attachment.Attachment;
import com.example.server.repository.attachment.AttachmentRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

        String path2images = getPath2Attachments(achievementId);
        for (Attachment attachment : attachments) {
            attachment.setImageBytes(Files.readAllBytes(Path.of(attachment.getPath())));
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
            throw new IOException("медиа не удалено: " + attachment.toString());
        }

        attachmentRepository.deleteById(id);
    }

    public void createAttachment(Attachment attachment) throws IOException {
        if (attachment.getImageBytes() == null) {
            throw new IllegalArgumentException("Не получено медиа " + attachment.toString());
        }

        attachmentRepository.save(attachment);
        Files.write(Path.of(attachment.getPath()), attachment.getImageBytes());
    }

    public void updateAttachment(Attachment changes) {
        if (changes.getId() == null) {
            throw new IllegalArgumentException("Не указан id attachment при обновлении");
        }

        Attachment newAttachment = getAttachmentById(changes.getId());
        if (changes.getTitle() != null) {
            newAttachment.setTitle(changes.getTitle());
        }
        if (changes.getImageBytes() != null) {
            newAttachment.setImageBytes(changes.getImageBytes());
        }

        attachmentRepository.save(newAttachment);
    }

    public static String getPath2Attachments(Long achievement_id) {
        return System.getProperty("user.dir") + "/src/main/attachments/achievement_" + achievement_id;
    }
}
