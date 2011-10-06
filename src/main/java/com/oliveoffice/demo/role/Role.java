package com.oliveoffice.demo.role;

import com.oliveoffice.demo.user.User;
import org.f0rb.demo._._Model;

import java.util.List;

/**
 * Class org.f0rb.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-29
 */
public class Role extends _Model {
    private Integer id;
    private String rolename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void fillByModel(Role role) {
        rolename = role.getRolename();
    }
}
