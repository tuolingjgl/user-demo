package com.oliveoffice.demo.listener;

import com.google.inject.*;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.struts2.Struts2GuicePluginModule;
import com.oliveoffice.demo.user.UserDAO;
import com.oliveoffice.demo.user.UserDTO;
import com.oliveoffice.demo.user.UserServiceImpl;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.f0rb.demo._._Service;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import javax.servlet.ServletContextEvent;
import java.lang.ref.PhantomReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Timer;

public class GlobalListener extends GuiceServletContextListener {
    private static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GlobalListener.class);


    public Injector getInjector() {
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
                myBatisProperties.setProperty("JDBC.schema", "user-demo");
                myBatisProperties.setProperty("JDBC.username", "sa");
                myBatisProperties.setProperty("JDBC.password", "");
                myBatisProperties.setProperty("JDBC.autoCommit", "false");
                return myBatisProperties;
            }
        };

        AbstractModule serviceModule = new AbstractModule() {
            @Override
            protected void configure() {
                bind(new TypeLiteral<_Service<UserDTO>>() {
                }).to(UserServiceImpl.class).in(Scopes.SINGLETON);
            }
        };
        return Guice.createInjector(struts2GuicePluginModule,
                servletModule, myBatisModule, serviceModule);
    }

    public void contextDestroyed(ServletContextEvent event) {
        Thread[] threads = getThreads();

        String[] guicePrefixes = {
                "com.google.inject.internal.",           // google guice 2.0
                "com.google.inject.internal.util.$"     // google guice 3.0
        };
        for (String guicePrefix : guicePrefixes) {
            for (Thread thread : threads) {
                if (thread == null) continue;

                // Try to shutdown the Finalizer Thread
                try {
                    if (thread.getClass().getName().equals(guicePrefix + "Finalizer")) {
                        Class mapMakerClass = Class.forName(guicePrefix + "MapMaker");

                        Class[] classes = mapMakerClass.getDeclaredClasses();
                        for (Class clazz : classes) {
                            if (clazz.getName().equals(guicePrefix + "MapMaker$QueueHolder")) {
                                Object finalizableReferenceQueue = getFieldInstance(null, clazz, "queue");
                                Object referenceQueue = getFieldInstance(finalizableReferenceQueue, finalizableReferenceQueue.getClass(), "queue");
                                Object finalizerQueue = getFieldInstance(thread, thread.getClass(), "queue");

                                // Check that the thread is our instance
                                if (referenceQueue == finalizerQueue) {
                                    PhantomReference frqReference = (PhantomReference) getFieldInstance(thread, thread.getClass(), "frqReference");

                                    // Notify the finalizer it can stop
                                    Method enqueue = finalizerQueue.getClass().getDeclaredMethod("enqueue", new Class[]{java.lang.ref.Reference.class});
                                    enqueue.setAccessible(true);
                                    enqueue.invoke(finalizerQueue, frqReference);
                                    LOGGER.info(thread.getClass().getName() + " successfully notified to shutdown.");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.warn(thread.getClass().getName() + " couldn't be notified to shutdown !", e);
                }

                // Try to cancel the Expiration Timer
                try {
                    if (thread.getClass().getName().equals("java.util.TimerThread")) {
                        Class expirationTimerClass = null;
                        try {
                            expirationTimerClass = Class.forName(guicePrefix + "ExpirationTimer");
                        } catch (Exception e) {
                            // Silently fail
                        }
                        if (expirationTimerClass != null) {
                            Timer instance = (Timer) getFieldInstance(null, expirationTimerClass, "instance");
                            Thread instanceThread = (Thread) getFieldInstance(instance, instance.getClass(), "thread");

                            // Check that the thread is our instance
                            if (instanceThread == thread) {
                                instance.cancel();
                                LOGGER.info(expirationTimerClass.getName() + " successfully cancelled.");
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.warn(thread.getClass().getName() + " couldn't be cancelled !", e);
                }
            }

        }
    }

    private Object getFieldInstance(Object instance, Class clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            return null;
        }
    }

    /* Code from apache tomcat 6.0.32 */
    private Thread[] getThreads() {
        // Get the current thread group
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        // Find the root thread group
        while (tg.getParent() != null) {
            tg = tg.getParent();
        }

        int threadCountGuess = tg.activeCount() + 50;
        Thread[] threads = new Thread[threadCountGuess];
        int threadCountActual = tg.enumerate(threads);
        // Make sure we don't miss any threads
        while (threadCountActual == threadCountGuess) {
            threadCountGuess *= 2;
            threads = new Thread[threadCountGuess];
            // Note tg.enumerate(Thread[]) silently ignores any threads that
            // can't fit into the array
            threadCountActual = tg.enumerate(threads);
        }

        return threads;
    }
}
