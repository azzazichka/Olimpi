package com.example.androidApp.model.entity;


public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer access_lvl = 0;

    public User(Long id, String name, String email, String password, Integer access_lvl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.access_lvl = access_lvl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccess_lvl() {
        return access_lvl;
    }

    public void setAccess_lvl(Integer access_lvl) {
        this.access_lvl = access_lvl;
    }
}


