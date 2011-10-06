package com.oliveoffice.demo.user;

import com.oliveoffice.demo.role.Role;
import org.f0rb.demo._._Model;
import org.f0rb.demo.utils.SecureUtils;

import java.sql.Timestamp;
import java.util.List;

/** Class org.f0rb.demo.pojo description goes here. */
public class User extends _Model {
    public final static String USERNAME = "username";
    public final static String NICKNAME = "nickname";
    public final static String PASSWORD = "password";

    public final static String FIELD_NEWPASS = "newpass";
    public final static String FIELD_CONFPASS = "confpass";

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String last_ip;
    private Timestamp latetime;
    private Timestamp regtime;
    private Integer online;
    private Boolean active;


    /** 用户注册, 更改密码时使用 */
    private String newpass;
    /** 确认密码, 更改密码时同newpass一起使用 */
    private String confpass;
    /** 是否自动登录 */
    private Boolean rememberMe;
    /** uuid. */
    private String uuid;
    private List<Role> roles;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

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

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getConfpass() {
        return confpass;
    }

    public void setConfpass(String confpass) {
        this.confpass = confpass;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User toSessionUser() {
        User o = new User();
        o.setId(id);
        o.setUsername(username);
        o.setNickname(nickname);
        o.setLatetime(latetime);
        o.setActive(active);
        return o;
    }

    public User toModel() {
        User user = new User();
        fillInModel(user);
        return user;
    }

    public void fillInModel(User user) {
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(SecureUtils.encryptPassword(newpass));
        user.setLast_ip(last_ip);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        latetime = now;
        user.setRegtime(now);
        user.setLatetime(latetime);
        user.setOnline(0);
        user.setActive(true);
    }
}
