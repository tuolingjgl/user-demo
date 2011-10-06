package com.oliveoffice.demo.action;

import com.oliveoffice.demo.user.User;
import com.oliveoffice.demo.user.UserAction;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.oliveoffice.demo.listener.GlobalListener.createInjector;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Class com.oliveoffice.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-27
 */
public class UserActionTests extends Struts2TestCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserActionTests.class);

    @Test
    public void testRegisterWithNewUsername() throws Exception {
        String key = String.valueOf(String.valueOf(Math.random()).hashCode());
        request.setParameter("username", "test" + key + "@yahoo.cn");
        request.setParameter("newpass", "123456");
        request.setParameter("confpass", "123456");
        request.setParameter("nickname", "test" + key);
        ActionProxy proxy = getActionProxy("/user/register");
        UserAction action = (UserAction) proxy.getAction();
        User o = action.getModel();
        String result = proxy.execute();
        LOGGER.info(response.getContentAsString());
        assertEquals(result, "/");
        assertNull(o.getMessages());
    }

    @Test
    public void testRegisterWithOldUsername() throws Exception {
        request.setParameter("username", "test@yahoo.cn");
        request.setParameter("newpass", "123456");
        request.setParameter("confpass", "123456");
        request.setParameter("nickname", "test");
        ActionProxy proxy = getActionProxy("/user/register");
        UserAction action = (UserAction) proxy.getAction();
        User o = action.getModel();
        String result = proxy.execute();
        LOGGER.info(response.getContentAsString());
        assertEquals(result, "register");
        assertEquals(o.getMessages().get("username").get(0), "账号已存在");
        assertEquals(o.getMessages().get("nickname").get(0), "昵称已存在");
    }

    @Test
    public void testLogin() throws Exception {
        request.setParameter("username", "f0rb@yahoo.cn");
        request.setParameter("password", "123456");
        ActionProxy proxy = getActionProxy("/user/login");
        UserAction action = (UserAction) proxy.getAction();
        User o = action.getModel();
        String result = proxy.execute();
        assertEquals(result, "/");
        assertNull(o.getMessages());
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testCheckUsernameWithOldUsername() throws Exception {
        request.setParameter("username", "f0rb@yahoo.cn");
        ActionProxy proxy = getActionProxy("/user/check-username");
        UserAction action = (UserAction) proxy.getAction();
        User o = action.getModel();
        String result = proxy.execute();
        assertEquals(result, "json");
        assertEquals(o.getMessages().get("username").get(0), "账号已存在");
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testCheckUsernameWithNewUsername() throws Exception {
        request.setParameter("username", "f0rb@qq.com");
        ActionProxy proxy = getActionProxy("/user/check-username");
        UserAction action = (UserAction) proxy.getAction();
        User o = action.getModel();
        String result = proxy.execute();
        assertEquals(result, "json");
        assertNull(o.getMessages());
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

