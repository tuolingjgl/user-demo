package com.oliveoffice.demo.userrole;

import org.f0rb.demo._._Model;

/**
 * Class org.f0rb.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-29
 */
public class UserRole extends _Model {
    private int id;
    private int user_id;
    private int role_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}
