package com.oliveoffice.demo.action;

import com.oliveoffice.demo.userrole.UserRole;
import com.oliveoffice.demo.userrole.UserRoleAction;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.oliveoffice.demo.listener.GlobalListener.createInjector;
import static org.junit.Assert.assertEquals;

/**
 * Class com.oliveoffice.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-27
 */
public class UserRoleActionTests extends Struts2TestCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserRoleActionTests.class);

    @Ignore
    @Test
    public void testInsert() throws Exception {
        request.setParameter("user_id", "1");
        request.setParameter("role_id", "4");
        ActionProxy proxy = getActionProxy("/userrole/insert");
        UserRoleAction action = (UserRoleAction) proxy.getAction();
        UserRole o = action.getModel();
        String result = proxy.execute();
        assertEquals(result, "json");
        assertEquals(o.getMessages(), null);
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

