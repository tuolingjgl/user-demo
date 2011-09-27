package com.oliveoffice.demo.user;

/**
 * Class org.f0rb.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-16
 */
public enum UserServiceResult {
    ROOT("/"),
    LOGIN("login"),
    REGISTER("register"),
    CODE_404("404"),
    JSON("json"),
    ;

    final private String name;

    UserServiceResult(String s) {
        name = s;
    }

    public String toString(){
        return name;
    }
}
