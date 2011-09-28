package com.oliveoffice.demo.listener; /**
* Class org.f0rb.demo.listener description goes here.
*
* @author Administrator
* @version 1.0.0 11-7-14
*/

import org.hsqldb.Server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/** 该类的职责是在WebApp启动时自动开启HSQL服务. 依然使用Server方式，不受AppServer的影响. */
public class HsqldbListener implements ServletContextListener {

    private static String ALIAS = "demo";

    /** Listener 初始化方法. */
    public void contextInitialized(ServletContextEvent sce) {
        // 启动hsql数据库
        Server.main(new String[]{
                "-database.0", sce.getServletContext().getRealPath("/") + "db/demodb",
                "-dbname.0", ALIAS,
                "-no_system_exit", "true"
        });
    }

    /** Listener销毁方法，在Web应用终止的时候执行"shutdown"命令关闭数据库. */
    public void contextDestroyed(ServletContextEvent arg0) {
        Connection conn = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" + ALIAS, "sa", "");
            Statement statement = conn.createStatement();
            statement.executeUpdate("SHUTDOWN;");
            statement.close();
            conn.close();
        } catch (Exception e) {
            // do nothing
        }
    }
}