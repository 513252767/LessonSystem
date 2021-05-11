package com.hung.util.spring.begin;

import com.hung.util.spring.ioc.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Hung
 */
public class SpringContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext applicationContext = new ApplicationContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
