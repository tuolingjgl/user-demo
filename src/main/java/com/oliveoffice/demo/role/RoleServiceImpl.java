package com.oliveoffice.demo.role;

import org.f0rb.demo._._Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Class com.oliveoffice.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-2
 */
public class RoleServiceImpl implements _Service<Role> {
    private final static Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

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

    public String execute(Role o) {
        return execute(o.getServiceName(), o);
    }

    public String execute(final String serviceName, Role o) {
        LOGGER.info("execute service [" + serviceName + "] start;");
        String service = convertServiceName(serviceName);
        String serviceResult;
        RoleServiceName roleServiceName;
        try {
            roleServiceName = RoleServiceName.valueOf(service);
        } catch (IllegalArgumentException e) {
            roleServiceName = RoleServiceName.UNIMPLEMENTED;
        }
        switch (roleServiceName) {
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
                serviceResult = RoleServiceResult.CODE_404.toString();
        }
        LOGGER.info("execute service [" + serviceName + "] with result: " + serviceResult);
        LOGGER.info("execute service [" + serviceName + "] done!");
        return serviceResult;
    }

    private String list(Role o) {
        o.setList(roleDAO.list());
        return RoleServiceResult.JSON.toString();
    }

    private String get(Role o) {
        final Integer id = o.getId();
        Role temp = roleDAO.get(id);
        o.fillByModel(temp);
        return RoleServiceResult.JSON.toString();
    }

    private String delete(Role o) {
        roleDAO.delete(o);
        return RoleServiceResult.JSON.toString();
    }

    private String update(Role o) {
        roleDAO.update(o);
        return RoleServiceResult.JSON.toString();
    }

    private String insert(Role o) {
        roleDAO.insert(o);
        return RoleServiceResult.JSON.toString();
    }
}
