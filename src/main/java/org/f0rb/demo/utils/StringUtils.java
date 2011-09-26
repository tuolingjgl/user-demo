package org.f0rb.demo.utils;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-2-28
 * Time: 15:25:43
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
    /**
    * 用户名必须是字母开头字母数字下划线组合的4到20位字符串
    *
    * @param username the name of User
    * @return True if username is illegal, otherwise false.
    */
    public static boolean isIllegalUsername(String username) {
        Pattern p = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return username == null || !p.matcher(username).matches();
    }

    /**
     * 空间地址必须是字母开头字母数字下划线组合的4到20位字符串
     *
     * @param zoneurl the url of User
     * @return True if zoneurl is illegal, otherwise false.
     */
    public static boolean isIllegalZoneurl(String zoneurl) {
        Pattern p = Pattern.compile("[a-zA-Z]\\w{3,19}");
        return !p.matcher(zoneurl).matches();
    }

    public static boolean isIllegalPassword(String password) {
        return StringUtils.isEmpty(password) || (password.length() < 6) || (password.length() > 20);
    }

    /**
    * 昵称必须为4-20个字节(一个汉字占2个字节)
    *
    * @param nickname the nickname of User
    * @return True if nickname is illegal, otherwise false.
    */
    public static boolean isIllegalNickname(String nickname) {
        Pattern p = Pattern.compile("[\\w\\u4e00-\\u9fa5]+");  // [\u4e00-\u9fa5]
        String v = StringUtils.trim(nickname);
        int len = 0;
        for (int ix = 0; ix < v.length(); ix++) {
            char ch = v.charAt(ix);
            if (!(ch > 255)) {
                len++;
            } else {
                len += 2;
            }
        }
        return len < 4 || len > 16 || !p.matcher(v).matches();
        //return !p.matcher(nickname).matches();
    }

    public static boolean isIllegalEmail(String email) {
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");  // [\u4e00-\u9fa5]
        return StringUtils.isEmpty(email) || !p.matcher(email).matches();
    }

}
