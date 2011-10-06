package com.oliveoffice.demo.userrole;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.f0rb.demo._._Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Class {@link UserRoleAction} description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-3
 */

@Actions({
        @Action(value = "{method:.+}", params = {"serviceName", "{1}"})
})
public class UserRoleAction implements ModelDriven<UserRole>{
    @Inject
    @Resource
    private UserRole userRole;
    @Inject
    @Resource
    private _Service<UserRole> userRoleSerivice;

    @Override
    public UserRole getModel() {
        return userRole;
    }

    public String execute() {
        return userRoleSerivice.execute(userRole);
    }
}

