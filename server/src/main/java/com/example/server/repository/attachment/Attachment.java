package com.example.server.repository.attachment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "attachments")
@Data
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long achievement_id;
    private String title;

    @Transient
    @JsonProperty
    private String imageBytesBase64;

    public String getPath() {
        return System.getProperty("user.dir") +
                "/src/main/attachments/achievement_"
                + achievement_id + "/" + id;
    }
}
