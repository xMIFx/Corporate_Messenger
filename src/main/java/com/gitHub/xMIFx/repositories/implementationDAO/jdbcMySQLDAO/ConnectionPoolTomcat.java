package com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * Created by Vlad on 12.07.2015.
 */
public class ConnectionPoolTomcat {
    private static ConnectionPoolTomcat conPool = new ConnectionPoolTomcat();
    private DataSource datasource;
    {
        datasource = new DataSource();
        setPropertiesForDataSource();
    }

    private ConnectionPoolTomcat() {

    }

    public static DataSource getDataSource() {
        return conPool.datasource;
    }

    private void setPropertiesForDataSource() {
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://127.0.0.1:3306/corporate_messenger");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("Lytghj12");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        datasource.setPoolProperties(p);

    }
}
