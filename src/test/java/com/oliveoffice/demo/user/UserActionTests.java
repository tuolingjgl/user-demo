package com.oliveoffice.demo.user;

import com.google.inject.*;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.google.inject.struts2.Struts2GuicePluginModule;
import com.opensymphony.xwork2.ActionProxy;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.f0rb.demo._._Service;
import org.hsqldb.Server;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Class com.oliveoffice.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-27
 */
public class UserActionTests extends StrutsTestCase {
    private static String ALIAS = "demo";
    public final static String INJECTOR_NAME = Injector.class.getName();
    public final static Injector injector = getInjector();

    static {
        setUpHsqldbServer();
    }

    public static Injector getInjector() {
        Struts2GuicePluginModule struts2GuicePluginModule = new Struts2GuicePluginModule();
        ServletModule servletModule = new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(StrutsPrepareAndExecuteFilter.class).in(Singleton.class);
                filter("/*").through(StrutsPrepareAndExecuteFilter.class);
            }
        };

        MyBatisModule myBatisModule = new MyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.HSQLDB_Server);
                bindDataSourceProviderType(PooledDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                Names.bindProperties(binder(), getConnection());
                addMapperClass(UserDAO.class);
            }

            private Properties getConnection() {
                final Properties myBatisProperties = new Properties();
                myBatisProperties.setProperty("mybatis.environment.id", "user-demo");
                myBatisProperties.setProperty("JDBC.schema", "demo");
                myBatisProperties.setProperty("JDBC.username", "sa");
                myBatisProperties.setProperty("JDBC.password", "");
                myBatisProperties.setProperty("JDBC.autoCommit", "false");
                return myBatisProperties;
            }
        };

        AbstractModule serviceModule = new AbstractModule() {
            @Override
            protected void configure() {
                bind(new TypeLiteral<_Service<UserDTO>>() {}).to(UserServiceImpl.class).in(Scopes.SINGLETON);
            }
        };
        return Guice.createInjector(struts2GuicePluginModule,
                servletModule, myBatisModule, serviceModule);
    }

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

    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectStrutsDependencies(injector);
        //servletContext.setAttribute(INJECTOR_NAME, injector);
    }

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

    protected void destroy() throws Throwable {
        System.out.println("over");
    }
}

