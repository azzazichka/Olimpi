package com.example.androidApp.model.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "achievements")
public class Achievement {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Long contest_id;

    public Achievement(Long id, Long contest_id) {
        this.id = id;
        this.contest_id = contest_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getContest_id() {
        return contest_id;
    }

    public void setContest_id(Long contest_id) {
        this.contest_id = contest_id;
    }
}
