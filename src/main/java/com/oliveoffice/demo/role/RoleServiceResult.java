package com.oliveoffice.demo.role;

/**
 * Class com.oliveoffice.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-3
 */
public enum RoleServiceResult {
    CODE_404("404"),
    JSON("json"),
    ;

    final private String name;

    RoleServiceResult(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
