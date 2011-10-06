package com.oliveoffice.demo.action;

import com.oliveoffice.demo.role.Role;
import com.oliveoffice.demo.role.RoleAction;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.*;

import static com.oliveoffice.demo.listener.GlobalListener.createInjector;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Class com.oliveoffice.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-27
 */
public class RoleActionTests extends Struts2TestCase {
    @Ignore
    @Test
    public void testInsert() throws Exception {
        request.setParameter("rolename", "普通用户");
        ActionProxy proxy = getActionProxy("/role/insert");
        RoleAction action = (RoleAction) proxy.getAction();
        Role o = action.getModel();
        String result = proxy.execute();
        assertEquals(result, "json");
        assertNull(o.getMessages());
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testGet() throws Exception {
        request.setParameter("id", "4");
        ActionProxy proxy = getActionProxy("/role/get");
        RoleAction action = (RoleAction) proxy.getAction();
        String result = proxy.execute();
        assertEquals(result, "json");
        Role o = action.getModel();
        assertNotNull(o.getRolename());
        System.out.println(response.getContentAsString());
    }

    @Before
    public void before() {
        setUp();
        injectStrutsDependencies(createInjector());
    }

    @BeforeClass
    public static void init() throws Throwable {
        setUpHsqldbServer();
    }

    @AfterClass
    public static void destroy() throws Throwable {
        shutDowntUpHsqldbServer();
    }
}

