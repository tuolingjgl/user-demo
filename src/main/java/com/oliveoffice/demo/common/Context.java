package com.oliveoffice.demo.common;

import com.oliveoffice.demo.user.User;
import org.apache.struts2.ServletActionContext;
import org.f0rb.demo.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Class org.f0rb.demo._ description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-17
 */
public class Context {
    public static HttpServletRequest getRequset() {
        return ServletActionContext.getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    public static void setLoginCookies(User o) {
        HttpServletRequest request = getRequset();
        HttpServletResponse response = getResponse();
        response.addCookie(RequestUtils.createCookie(request, CONSTANT.COOKIE_LASTLOGIN, o.getLatetime().toString(), -1));
        response.addCookie(RequestUtils.createCookie(request, CONSTANT.COOKIE_UUID, o.getUuid(),
                Boolean.TRUE.equals(o.getRememberMe()) ? CONSTANT.HOLDDAYS * 86400 : -1));
        try {
            response.addCookie(RequestUtils.createCookie(request, CONSTANT.COOKIE_NICKNAME, URLEncoder.encode(o.getNickname(), "utf-8"),
                    Boolean.TRUE.equals(o.getRememberMe()) ? CONSTANT.HOLDDAYS * 86400 : -1));
        } catch (UnsupportedEncodingException e) { //nothing
        }
    }

    public static void clearLoginCookies() {
        HttpServletRequest request = getRequset();
        HttpServletResponse response = getResponse();
        response.addCookie(RequestUtils.createCookie(request, CONSTANT.COOKIE_UUID, "", 0));
        response.addCookie(RequestUtils.createCookie(request, CONSTANT.COOKIE_LASTLOGIN, "", 0));
        response.addCookie(RequestUtils.createCookie(request, CONSTANT.COOKIE_NICKNAME, "", 0));
    }
}
