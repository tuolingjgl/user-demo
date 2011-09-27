package org.f0rb.demo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-2-25
 * Time: 21:48:34
 * To change this template use File | Settings | File Templates.
 */
public class RequestUtils extends org.apache.struts2.RequestUtils {

    public static Cookie createCookie(HttpServletRequest request, String name, String value) {
        return createCookie(request, name, value, 0);
    }

    public static Cookie createCookie(HttpServletRequest request, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        String domain = request.getServerName();
        if (domain != null && domain.indexOf('.') != -1) {
            cookie.setDomain('.' + domain);
        }
        cookie.setPath("/");
        return cookie;
    }

    /**
     * 获取COOKIE
     *
     * @param request HttpServletRequest Object
     * @param name    the name of the cookie
     * @return        The cookie named 'name' if exists, otherwise null.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    	/**
	 * 获取header信息，名字大小写无关
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getHeader(HttpServletRequest req, String name){
		String value = req.getHeader(name);
		if(value!=null)
			return value;
		Enumeration<?> names = req.getHeaderNames();
		while(names.hasMoreElements()){
			String n = (String)names.nextElement();
			if(n.equalsIgnoreCase(name)){
				return req.getHeader(n);
			}
		}
		return null;
	}

}
