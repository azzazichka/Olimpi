package com.example.androidApp.model.entity;



public class Achievement {
    private Long id;
    private Long contest_id;
    private Long user_id;

    public Achievement(Long user_id, Long contest_id) {
        this.user_id = user_id;
        this.contest_id = contest_id;
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


    public Long getContest_id() {
        return contest_id;
    }

    public void setContest_id(Long contest_id) {
        this.contest_id = contest_id;
    }
}
