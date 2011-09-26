package com.oliveoffice.demo.common;

/**
 * Created by IntelliJ IDEA.
 * Author: f0rb
 * Date: 2010-6-22
 * Time: 11:11:45
 * To change this template use File | Settings | File Templates.
 */
public interface MessageKey {
    public final String FIELD_INVALID = "field.invalid";
    public final String DOT = ".";
    public final String FIELD = "field";
    public final String EXISTENT = "existent";
    public final String UNEXISTENT = "unexistent";
    public final String FIELD_EXISTENT = FIELD + DOT + EXISTENT;
    public final String FIELD_UNEXISTENT = FIELD + DOT + UNEXISTENT;

    public final String USERNAME_INVALID = "user.username.invalid";
    public final String NEWPASS_INVALID = "user.newpass.invalid";
    public final String CONFPASS_MISMATCH = "user.confpass.mismatch";
    public final String PASSWORD_INCORRECT = "user.password.incorrect";
    public final String NICKNAME_INVALID = "user.nickname.invalid";
    public final String CATEGORYNAME_INVALID = "category.name.invalid";
    public final String VERIFYCODE_INVALID = "verifycode.invalid";

    public final String USERNAME_EXISTENT = "user.username.existent";
    public final String NICKNAME_EXISTENT = "user.nickname.existent";
    public final String CATEGORY_UNEXISTENT = "category.id.unexistent";
    public final String ARTICLE_UNEXISTENT = "article.id.unexistent";
    public final String USERNAME_UNEXISTENT = "user.username.unexistent";
    public final String USER_REGISTER_ERROR = "user.register.error";

    public final String SESSION_EXPIRED = "session.expired";
}
