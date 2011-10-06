package com.oliveoffice.demo.privilege;

import org.f0rb.demo._._Model;

/**
 * Class com.oliveoffice.demo.privilege description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-6
 */
public class priviledge extends _Model {
    private Integer id;
    private String priviledgename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPriviledgename() {
        return priviledgename;
    }

    public void setPriviledgename(String priviledgename) {
        this.priviledgename = priviledgename;
    }
}
