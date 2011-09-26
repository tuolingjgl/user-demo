package com.oliveoffice.demo.user;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.f0rb.demo._._Action;
import org.f0rb.demo._._Service;

import javax.annotation.Resource;
import javax.inject.Inject;


/**
 * Created by IntelliJ IDEA.
 *
 * @author f0rb
 *         Date: 2010-6-26
 *         Time: 22:08:31
 */

@Results({
        @Result(name = "/", type = "redirect", location = "/"),
        @Result(name = "login", location = "/WEB-INF/jsp/user/login.jsp"),
        @Result(name = "register", location = "/WEB-INF/jsp/user/register.jsp"),
        @Result(name = "json", type = "json", params = {
                "excludeNullProperties", "true",
                "ignoreHierarchy", "false"
        })
})
@Actions({
        @Action(value = "{method:.+}", params = {"serviceName", "{1}"})
})
public class UserAction extends _Action<UserDTO> {
    public UserAction() {
        super(new UserDTO());
    }

    /**
     * 给Spring的注解注入提供一个接口
     * @param service org.f0rb.demo.user.UserServiceImpl 的实例
     */
    @Inject
    @Resource
    public void setUserService(_Service<UserDTO> service){
        setService(service);
    }
}
