package com.oliveoffice.demo.user;

import com.oliveoffice.demo.common.CONSTANT;
import com.oliveoffice.demo.common.Context;
import com.oliveoffice.demo.common.MessageKey;
import org.f0rb.demo._._Service;
import org.f0rb.demo.utils.SecureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.regex.Pattern;

/**
 * UserServiceImpl.
 *
 * @author f0rb
 * @version 1.0.0 2010-1-27
 */
public class UserServiceImpl implements _Service<UserDTO> {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    @Resource
    private UserDAO userDAO;

    /**
     * 将{@code serviceName}全部转为大写，并且将短横线(-)转为下划线(_)，
     * 以方便调用{@link com.oliveoffice.demo.user.UserServiceName#valueOf(String)}
     * 例如 "check-username" -> "CHECK_USERNAME"
     * </p>
     * PS: 使用短横线方便做SEO
     *
     * @param serviceName serviceName
     * @return 大写加下划线组合的字符串
     * @see com.oliveoffice.demo.user.UserServiceName
     */
    private String convertServiceName(String serviceName) {
        return serviceName.toUpperCase().replaceAll("-", "_");
    }

    public String execute(UserDTO o) {
        return execute(o.getServiceName(), o);
    }

    public String execute(final String serviceName, UserDTO o) {
        LOGGER.info("execute service [" + serviceName + "] start;");
        String service = convertServiceName(serviceName);
        String serviceResult;
        UserServiceName userServiceName;
        try {
            userServiceName = UserServiceName.valueOf(service);
        } catch (IllegalArgumentException e) {
            userServiceName = UserServiceName.UNIMPLEMENTED;
        }
        switch (userServiceName) {
            case LOGIN:
                serviceResult = login(o);
                break;
            case CREATE:
                serviceResult = create(o);
                break;
            case REGISTER:
                serviceResult = register(o);
                break;
            case LOGOUT:
                serviceResult = logout(o);
                break;
            case INDEX:
                serviceResult = index(o);
                break;
            case CHECK_USERNAME:
                serviceResult = checkUsername(o);
                break;
            case CHECK_NEWPASS:
                serviceResult = checkNewpass(o);
                break;
            case CHECK_NICKNAME:
                serviceResult = checkNickname(o);
                break;
            default:
                serviceResult = UserServiceResult.CODE_404.toString();
                //throw new IllegalArgumentException("Service " + serviceName + "is unimplemented yet!");
        }
        LOGGER.info("execute service [" + serviceName + "] with result: " + serviceResult);
        LOGGER.info("execute service [" + serviceName + "] done!");
        return serviceResult;
    }

    /**
     * Method login ...
     * TODO: 3次登录失败则添加验证码校验
     *
     * @param o of type UserDTO
     * @return {@link UserServiceResult} to decide the view
     */
    public String login(UserDTO o) {
        final String ServiceName = "login";
        if ("GET".equalsIgnoreCase(Context.getRequset().getMethod())) {
            return UserServiceResult.LOGIN.toString();
        }
        final String username = o.getUsername();
        final String password = o.getPassword();
        /**
         * 当用户名不合法时, 提示USERNAME_UNEXISTENT 而不是 USERNAME_INVALID
         * 因为数据库中肯定不会存在不合法的用户名，这样可以避免对数据库进行无谓的查询,
         * 注: 这种校验一般放在页面上用js执行
         * 下同
         */
        if (isInvalidUsername(username)) {
            o.addMessageKey(ServiceName, MessageKey.USERNAME_UNEXISTENT);
            return UserServiceResult.LOGIN.toString();
        }
        // TODO: 完善业务处理过程，提高性能，如尽可能减少数据库查询
        User user = userDAO.getByUsername(username);
        if (user == null) {
            o.addMessageKey(ServiceName, MessageKey.USERNAME_UNEXISTENT);
            return UserServiceResult.LOGIN.toString();
        }
        if (!user.getPassword().equalsIgnoreCase(SecureUtils.encryptPassword(password))) {
            o.addMessageKey(ServiceName, MessageKey.PASSWORD_INCORRECT);
            return UserServiceResult.LOGIN.toString();
        }
        user.setLast_ip(Context.getRequset().getRemoteAddr());
        user.setOnline(user.getOnline() + 1);
        user.setLatetime(new Timestamp(System.currentTimeMillis()));
        o.fillByPojo(user);
        userDAO.login(user);
        Context.getRequset().getSession().setAttribute(CONSTANT.SESSION_USERDTO, o.toSessionUserDTO());
        Context.setLoginCookies(o);
        return UserServiceResult.ROOT.toString();
    }

    /**
     * Method logout ...
     *
     * @param o of type UserDTO
     * @return true if service succeed, otherwise false
     */
    public String logout(UserDTO o) {
        UserDTO sessionUserDTO = (UserDTO) Context.getSession().getAttribute(CONSTANT.SESSION_USERDTO);
        if (sessionUserDTO != null) {
            final int id = sessionUserDTO.getId();
            userDAO.logout(id);
            Context.getSession().removeAttribute(CONSTANT.SESSION_USERDTO);
            Context.clearLoginCookies();
        }
        return UserServiceResult.ROOT.toString();
    }

    /**
     * 创建一个新的用户, 并激活空间, 自动创建默认日志分类
     *
     * @param o user
     * @return true if service succeed, otherwise false
     */
    public String register(UserDTO o) {
        if ("GET".equalsIgnoreCase(Context.getRequset().getMethod())) {
            return UserServiceResult.REGISTER.toString();
        }

        //检验字段是否合法 o.username o.newpass o.confoass o.nickname
        checkUsername(o);
        checkNewpass(o);
        checkConfpass(o);
        checkNickname(o);

        if (o.hasMessages()) {
            o.addMessageKey("register", MessageKey.USER_REGISTER_ERROR);
            return UserServiceResult.REGISTER.toString();
        }

        o.setRemoteIP(Context.getRequset().getRemoteAddr());
        Integer id = userDAO.save(o.toPojo());
        o.setId(id);
        //注册后自动登录,
        Context.getRequset().getSession().setAttribute(CONSTANT.SESSION_USERDTO, o.toSessionUserDTO());
        Context.setLoginCookies(o);
        return UserServiceResult.ROOT.toString();
    }

    /**
     * uri matches "/user/check-username?username=xxx"
     *
     * @param o
     * @return "json"
     */
    public String checkUsername(final UserDTO o) {
        final String username = o.getUsername();
        final String FIELD = UserDTO.FIELD_USERNAME;
        if (isInvalidUsername(username)) {
            o.addMessageKey(FIELD, MessageKey.USERNAME_INVALID);
        } else if (hasUsername(username)) {
            o.addMessageKey(FIELD, MessageKey.USERNAME_EXISTENT);
        }
        return UserServiceResult.JSON.toString();
    }

    public String checkNewpass(final UserDTO o) {
        if (isInvalidPassword(o.getNewpass())) {
            o.addMessageKey(UserDTO.FIELD_NEWPASS, MessageKey.NEWPASS_INVALID);
        }
        return UserServiceResult.JSON.toString();
    }

    public String checkConfpass(UserDTO o) {
        final String newpass = o.getNewpass();
        final String confpass = o.getConfpass();
        if (confpass == null || !confpass.equals(newpass)) {
            o.addMessageKey(UserDTO.FIELD_CONFPASS, MessageKey.CONFPASS_MISMATCH);
        }
        return UserServiceResult.JSON.toString();
    }

    public String checkNickname(final UserDTO o) {
        final String nickname = o.getNickname();
        final String FIELD = UserDTO.FIELD_NICKNAME;
        if (isInvalidNickname(nickname)) {
            o.addMessageKey(FIELD, MessageKey.NICKNAME_INVALID);
        } else if (hasNickname(nickname)) {
            o.addMessageKey(FIELD, MessageKey.NICKNAME_EXISTENT);
        }
        return UserServiceResult.JSON.toString();
    }

    public String create(UserDTO o) {
        return UserServiceResult.CODE_404.toString();
    }

    public String index(UserDTO o) {
        return UserServiceResult.CODE_404.toString();
    }

    /**
     * 用户名必须是有效的Email地址
     *
     * @param username the name of User
     * @return True if username is illegal, otherwise false.
     */
    private boolean isInvalidUsername(String username) {
        Pattern p = Pattern.compile("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return username == null || !p.matcher(username).matches();
    }


    /**
     * check if the password is invalid
     *
     * @param password user's password
     * @return false if password is null or password's length is not between 6 and 20, otherwise true
     */
    private boolean isInvalidPassword(String password) {
        return password == null || !(6 <= password.length() && password.length() <= 20);
    }

    /**
     * 昵称必须为4-20个字节(一个汉字占2个字节)
     *
     * @param nickname the nickname of User
     * @return True if nickname is illegal, otherwise false.
     */
    private boolean isInvalidNickname(String nickname) {
        if (nickname == null) return true;
        Pattern p = Pattern.compile("[\\w\\u4e00-\\u9fa5]+");  // [\u4e00-\u9fa5]
        String v = nickname.trim();
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

    private boolean hasUsername(String username) {
        return userDAO.countByUsername(username) > 0;
    }

    private boolean hasNickname(String nickname) {
        return userDAO.countByNickname(nickname) > 0;
    }

    public static void main(String[] args) {
    }
}
