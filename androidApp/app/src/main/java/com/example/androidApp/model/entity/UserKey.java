package com.example.androidApp.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "users_keys")
public class UserKey {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long user_id;
    private String key;

    public UserKey(Long id, Long user_id, String key) {
        this.id = id;
        this.user_id = user_id;
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}