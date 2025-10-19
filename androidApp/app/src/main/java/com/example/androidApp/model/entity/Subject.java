package com.example.androidApp.model.entity;

public class Subject {
    private Long id;
    private String subject;
    private Long contest_id;

    public Subject(Long id, String subject, Long contest_id) {
        this.id = id;
        this.subject = subject;
        this.contest_id = contest_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getContest_id() {
        return contest_id;
    }

    public void setContest_id(Long contest_id) {
        this.contest_id = contest_id;
    }
}

