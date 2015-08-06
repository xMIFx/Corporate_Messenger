package com.gitHub.xMIFx.view.servlets.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class StartDestroyContextListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(StartDestroyContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("context init");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("context destroy");
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOGGER.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                LOGGER.error(String.format("Error deregistering driver %s", driver), e);
            }

        }

    }
}
