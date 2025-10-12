package com.example.androidApp.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "attachments")
public class Attachment {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long achievement_id;
    private String title;

    @Ignore
    private byte[] imageBytes;

    public String getPath() {
        return System.getProperty("user.dir") +
                "/src/main/attachments/achievement_"
                + achievement_id + "/" + id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(Long achievement_id) {
        this.achievement_id = achievement_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Attachment(Long id, Long achievement_id, String title) {
        this.id = id;
        this.achievement_id = achievement_id;
        this.title = title;
    }
}
