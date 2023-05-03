package com.zav.appointment.domain;


public class User {
    private Long id;

    private Integer practiceId;

    private String name;

    private String password;

    private Integer state;

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(Integer practiceId) {
        this.practiceId = practiceId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
