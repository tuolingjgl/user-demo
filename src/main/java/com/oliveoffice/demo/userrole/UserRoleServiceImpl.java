package com.oliveoffice.demo.userrole;

import com.oliveoffice.demo.role.RoleDAO;
import com.oliveoffice.demo.user.UserDAO;
import com.oliveoffice.demo.user.UserServiceResult;
import org.f0rb.demo._._Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Class com.oliveoffice.demo.UserRole description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-3
 */
public class UserRoleServiceImpl implements _Service<UserRole> {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Inject
    @Resource
    private UserRoleDAO userRoleDAO;
    @Inject
    @Resource
    private UserDAO userDAO;
    @Inject
    @Resource
    private RoleDAO roleDAO;

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

    public String execute(UserRole o) {
        return execute(o.getServiceName(), o);
    }

    public String execute(final String serviceName, UserRole o) {
        LOGGER.info("execute service [" + serviceName + "] start;");
        String service = convertServiceName(serviceName);
        String serviceResult;
        UserRoleServiceName userRoleServiceName;
        try {
            userRoleServiceName = UserRoleServiceName.valueOf(service);
        } catch (IllegalArgumentException e) {
            userRoleServiceName = UserRoleServiceName.UNIMPLEMENTED;
        }
        switch (userRoleServiceName) {
            case INSERT:
                serviceResult = insert(o);
                break;
            case UPDATE:
                serviceResult = update(o);
                break;
            case DELETE:
                serviceResult = delete(o);
                break;
            case GET:
                serviceResult = get(o);
                break;
            case LIST:
                serviceResult = list(o);
                break;
            default:
                serviceResult = UserRoleServiceResult.CODE_404.toString();
        }
        LOGGER.info("execute service [" + serviceName + "] with result: " + serviceResult);
        LOGGER.info("execute service [" + serviceName + "] done!");
        return serviceResult;
    }

    private String list(UserRole o) {
        return "404";
    }

    private String get(UserRole o) {
        final Integer id = o.getId();
        userRoleDAO.get(id);
        return UserServiceResult.JSON.toString();
    }

    private String delete(UserRole o) {
        final Integer id = o.getId();
        userRoleDAO.delete(o);
        return UserServiceResult.JSON.toString();
    }

    private String update(UserRole o) {
        return "404";
    }

    private String insert(UserRole o) {
        final Integer user_id = o.getUser_id();
        final Integer role_id = o.getRole_id();

        if (user_id == null || userDAO.load(user_id).getId() == null ) {
            o.addMessageKey("user_id", "user.unexistent");
        }
        if (role_id == null || roleDAO.load(role_id).getId() == null ) {
            o.addMessageKey("role_id", "role.unexistent");
        }
        if (!o.hasMessages()){
            userRoleDAO.insert(o);
        }
        return UserRoleServiceResult.JSON.toString();
    }
}
