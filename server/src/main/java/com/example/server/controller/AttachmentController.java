package com.example.server.controller;


import com.example.server.repository.attachment.Attachment;
import com.example.server.service.AttachmentService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;

@RestController
@RequestMapping(path = "api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping
    public List<Attachment> getAttachments(@RequestParam Long id) throws IOException {
        return attachmentService.getAttachments(id);
    }

    @PostMapping
    public void createAttachment(@RequestBody Attachment attachment) throws IOException {
        attachmentService.createAttachment(attachment);
    }

    @DeleteMapping
    public void deleteAttachment(@RequestParam Long id) throws IOException {
        attachmentService.deleteAttachment(id);
    }

    @PutMapping
    public void updateAttachment(@RequestBody Attachment changes) {
        attachmentService.updateAttachment(changes);
    }
}

// TODO: надо в response возвращать id созданного изображения