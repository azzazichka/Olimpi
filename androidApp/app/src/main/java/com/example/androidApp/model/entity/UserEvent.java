package com.example.androidApp.model.entity;


import java.util.Date;

public class UserEvent {
    private Long id;

    private String title;

    private Date start_time;

    private Date end_time;

    private Date notification_time;

    private Long contest_id;

    private Long user_id;

    public UserEvent(Long id, String title, Date start_time, Date end_time, Date notification_time, Long contest_id, Long user_id) {
        this.id = id;
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.notification_time = notification_time;
        this.contest_id = contest_id;
    }

    public UserEvent() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "UserEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", notification_time=" + notification_time +
                ", contest_id=" + contest_id +
                '}';
    }

    public Long getContest_id() {
        return contest_id;
    }

    public void setContest_id(Long contest_id) {
        this.contest_id = contest_id;
    }
}
