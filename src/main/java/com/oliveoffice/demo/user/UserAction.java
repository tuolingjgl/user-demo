package com.oliveoffice.demo.user;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
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
public class UserAction implements ModelDriven<User> {
    @Inject
    @Resource
    private User user;
    @Inject
    @Resource
    private _Service<User> userSerivice;

    @Override
    public User getModel() {
        return user;
    }

    public String execute() {
        return userSerivice.execute(user);
    }
}
