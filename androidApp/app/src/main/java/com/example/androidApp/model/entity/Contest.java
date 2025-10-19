package com.example.androidApp.model.entity;


import java.util.Date;
import java.util.List;


public class Contest {
    private Long id;

    private String title;
    private Integer lvl;

    private Date date_start;

    private Date date_end;

    private String link;
    private Integer low_grade;
    private Integer up_grade;
    private boolean archived;

    List<String> subjects;

    public Contest(Long id, String title, Integer lvl, Date date_start, Date date_end, String link, Integer low_grade, Integer up_grade, boolean archived) {
        this.id = id;
        this.title = title;
        this.lvl = lvl;
        this.date_start = date_start;
        this.date_end = date_end;
        this.link = link;
        this.low_grade = low_grade;
        this.up_grade = up_grade;
        this.archived = archived;
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

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getLow_grade() {
        return low_grade;
    }

    public void setLow_grade(Integer low_grade) {
        this.low_grade = low_grade;
    }

    public Integer getUp_grade() {
        return up_grade;
    }

    public void setUp_grade(Integer up_grade) {
        this.up_grade = up_grade;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}


