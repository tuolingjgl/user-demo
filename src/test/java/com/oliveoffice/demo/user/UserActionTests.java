package com.oliveoffice.demo.user;

import com.opensymphony.xwork2.ActionProxy;
import org.hsqldb.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.oliveoffice.demo.listener.GlobalListener.createInjector;
import static org.junit.Assert.*;

/**
 * Class com.oliveoffice.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-27
 */
public class UserActionTests extends Struts2TestCase {
    @Test
    public void testCheckUsernameWithOldUsername() throws Exception {
        request.setParameter("username", "f0rb@yahoo.cn");
        ActionProxy proxy = getActionProxy("/user/check-username");
        UserAction action = (UserAction) proxy.getAction();
        UserDTO o = action.getModel();
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
        UserDTO o = action.getModel();
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

    private final static String ALIAS = "demo";

    /** Listener 初始化方法. */
    public static void setUpHsqldbServer() {
        // 启动hsql数据库
        Server.main(new String[]{
                "-database.0", UserActionTests.class.getResource("/").getPath() + "db/demodb",
                "-dbname.0", ALIAS,
                "-no_system_exit", "false"
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Listener销毁方法，在Web应用终止的时候执行"shutdown"命令关闭数据库. */
    public static void shutDowntUpHsqldbServer() {
        Connection conn = null;
        Statement statement = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" + ALIAS, "sa", "");
            statement = conn.createStatement();
            statement.executeUpdate("SHUTDOWN;");
            statement.close();
            conn.close();
            System.out.println("SHUTDOWN HSQLDB successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}

