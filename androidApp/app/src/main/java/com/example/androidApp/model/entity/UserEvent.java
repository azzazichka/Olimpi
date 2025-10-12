package com.example.androidApp.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.androidApp.model.entity.type_converters.ContestDateConverter;
import com.example.androidApp.model.entity.type_converters.EventDateConverter;

import java.util.Date;

@Entity(tableName = "user_events")
public class UserEvent {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String title;

    @TypeConverters({EventDateConverter.class})
    private Date start_time;

    @TypeConverters({EventDateConverter.class})
    private Date end_time;

    @TypeConverters({EventDateConverter.class})
    private Date notification_time;
    
    private Long user_id;
    private Long contest_id;

    public UserEvent(Long id, String title, Date start_time, Date end_time, Date notification_time, Long user_id, Long contest_id) {
        this.id = id;
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.notification_time = notification_time;
        this.user_id = user_id;
        this.contest_id = contest_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(Date notification_time) {
        this.notification_time = notification_time;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getContest_id() {
        return contest_id;
    }

    public void setContest_id(Long contest_id) {
        this.contest_id = contest_id;
    }
}
