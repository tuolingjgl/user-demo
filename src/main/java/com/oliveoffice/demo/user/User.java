package com.oliveoffice.demo.user;

import java.sql.Timestamp;

/** Class org.f0rb.demo.pojo description goes here. */
public class User {
    public final static String USERNAME = "username";
    public final static String NICKNAME = "nickname";
    public final static String PASSWORD = "password";

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String last_ip;
    private Timestamp latetime;
    private Timestamp regtime;
    private Integer online;
    private Boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }

    public Timestamp getLatetime() {
        return latetime;
    }

    public void setLatetime(Timestamp latetime) {
        this.latetime = latetime;
    }

    public Timestamp getRegtime() {
        return regtime;
    }

    public void setRegtime(Timestamp regtime) {
        this.regtime = regtime;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
