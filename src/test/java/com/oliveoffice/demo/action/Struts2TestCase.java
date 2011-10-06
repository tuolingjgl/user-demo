package com.oliveoffice.demo.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.inject.Container;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.util.StrutsTestCaseHelper;
import org.hsqldb.Server;
import org.springframework.mock.web.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Class com.oliveoffice.demo.user description goes here.
 *
 * @author Administrator
 * @version 1.0.0 11-9-28
 */
public class Struts2TestCase {
    protected static MockServletContext servletContext;
    protected static MockServletConfig servletConfig;
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected MockHttpSession session;
    protected MockPageContext pageContext;
    protected ConfigurationManager configurationManager;
    protected Configuration configuration;
    protected Container container;
    protected Map<String, String> dispatcherInitParams;

    /**
     * Creates an action proxy for a request, and sets parameters of the ActionInvocation to the passed
     * parameters. Make sure to set the request parameters in the protected "request" object before calling this method.
     */
    protected ActionProxy getActionProxy(String uri) {
        request.setRequestURI(uri);
        ActionMapping mapping = getActionMapping(request);
        String namespace = mapping.getNamespace();
        String name = mapping.getName();
        String method = mapping.getMethod();

        Configuration config = configurationManager.getConfiguration();
        ActionProxy proxy = config.getContainer().getInstance(ActionProxyFactory.class)
                .createActionProxy(namespace, name, method, new HashMap<String, Object>(), true, false);

        ActionContext invocationContext = proxy.getInvocation().getInvocationContext();
        invocationContext.setParameters(new HashMap<String, Object>(request.getParameterMap()));
        // set the action context to the one used by the proxy
        ActionContext.setContext(invocationContext);

        // this is normaly done in onSetUp(), but we are using Struts internal
        // objects (proxy and action invocation)
        // so we have to hack around so it works
        ServletActionContext.setServletContext(servletContext);
        ServletActionContext.setRequest(request);
        ServletActionContext.setResponse(response);
        return proxy;
    }

    /**
     * Finds an ActionMapping for a given request
     *
     * @param request HttpServletRequest
     * @return ActionMapping
     */
    protected ActionMapping getActionMapping(HttpServletRequest request) {
        return Dispatcher.getInstance().getContainer().getInstance(ActionMapper.class)
                .getMapping(request, Dispatcher.getInstance().getConfigurationManager());
    }


    /**
     * Injects dependencies on an Object using Struts internal IoC container
     *
     * @param object IoC container
     */
    protected void injectStrutsDependencies(Object object) {
        Dispatcher.getInstance().getContainer().inject(object);
    }

    /**
     * Sets up the configuration settings, XWork configuration, and
     * message resources
     */
    protected void setUp() {
        initServletMockObjects();
        beforeInitDispatcher();
        initDispatcher(dispatcherInitParams);
    }

    protected void initServletMockObjects() {
        servletContext = new MockServletContext();
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session);
        pageContext = new MockPageContext(servletContext, request, response);
    }

    protected void beforeInitDispatcher() {
    }

    protected Dispatcher initDispatcher(Map<String, String> params) {
        Dispatcher du = StrutsTestCaseHelper.initDispatcher(servletContext, params);
        configurationManager = du.getConfigurationManager();
        configuration = configurationManager.getConfiguration();
        container = configuration.getContainer();
        return du;
    }

    private final static String ALIAS = "demo";

    /** Listener 初始化方法. */
    public static void setUpHsqldbServer() {
        // 启动hsql数据库
        Server.main(new String[]{
                "-database.0", UserActionTests.class.getResource("/").getPath() + "db/demodb",
                "-dbname.0", ALIAS,
                "-no_system_exit", "false",
                "encoding", "UTF-8",
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
