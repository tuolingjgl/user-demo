package com.oliveoffice.demo.userrole;

/**
 * Class com.oliveoffice.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-3
 */
public enum UserRoleServiceResult {
    CODE_404("404"),
    JSON("json"),
    ;

    final private String name;

    UserRoleServiceResult(String name) {
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
