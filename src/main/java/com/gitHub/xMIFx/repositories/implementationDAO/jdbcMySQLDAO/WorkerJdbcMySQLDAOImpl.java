package com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;



/**
 * Created by Vlad on 29.06.2015.
 */
public class WorkerJdbcMySQLDAOImpl implements WorkerDAO {
    private static final Logger logger = LoggerFactory.getLogger(WorkerJdbcMySQLDAOImpl.class.getName());
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/testBase1_0?user=root&password=Lytghj12";
    private static DataSource datasource;
    static{
        datasource = new DataSource();
        setPropertiesForDataSource();
    }


    @Override
    public Long save(Worker worker) {
      /*  try (Connection con = DriverManager.getConnection(JDBC_URL)) {
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try(Connection con = datasource.getConnection();){

        } catch (SQLException e) {
            logger.error("Exception when saving worker to MySQL: ", e);
        }
        return null;
    }

    @Override
    public Worker getById(Long id) {
        return null;
    }

    @Override
    public Worker getByName(String name) {
        return null;
    }

    @Override
    public Worker getByLoginPassword(String login, String pass) {
        return null;
    }

    @Override
    public List<Worker> getAll() {
        return null;
    }

    @Override
    public List<Worker> getByDepartment(Department department) {
        return null;
    }

    @Override
    public boolean remove(Worker worker) {
        return false;
    }

    @Override
    public boolean update(Worker worker) {
        return false;
    }

    private static void setPropertiesForDataSource(){
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://127.0.0.1:3306/testBase1_0");
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
