package com.oliveoffice.demo.role;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.f0rb.demo._._Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Class com.oliveoffice.demo.role description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-10-2
 */
@Actions({
        @Action(value = "{method:.+}", params = {"serviceName", "{1}"})
})
public class RoleAction implements ModelDriven<Role> {
    @Inject
    @Resource
    private Role role;
    @Inject
    @Resource
    private _Service<Role> roleSerivice;

    @Override
    public Role getModel() {
        return role;
    }

    public String execute() {
        return roleSerivice.execute(role);
    }
}
