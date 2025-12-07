package com.example.androidApp.model.entity;

public class Attachment {
    private Long id;
    private Long achievement_id;
    private String title;

    private byte[] imageBytes;
    private String imageBytesBase64;


    public Long getId() {
        return id;
    }

    public String getImageBytesBase64() {
        return imageBytesBase64;
    }

    public void setImageBytesBase64(String imageBytesBase64) {
        this.imageBytesBase64 = imageBytesBase64;
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

    public Attachment() {}

    public Attachment(Long id, Long achievement_id, String title) {
        this.id = id;
        this.achievement_id = achievement_id;
        this.title = title;
    }
}
