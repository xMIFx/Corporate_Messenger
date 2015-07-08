package com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vlad on 29.06.2015.
 */
public class WorkerJdbcMySQLDAOImpl implements WorkerDAO {
    private static final Logger logger = LoggerFactory.getLogger(WorkerJdbcMySQLDAOImpl.class.getName());
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/testBase1_0?user=root&password=Lytghj12";
    private static DataSource datasource;

    static {
        datasource = new DataSource();
        setPropertiesForDataSource();
    }


    @Override
    public Long save(Worker worker) {
        if (worker.getId() != null) {
            return worker.getId();
        }
        String sqlCreate = "INSERT INTO corporate_messenger.workers " +
                "(name, login, password, objectVersion) " +
                "VALUES (?, ?, ?, ?);";
        Long autoIncKeyId = -1L;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, worker.getName());
            st.setString(2, worker.getLogin());
            st.setString(3, worker.getPassword());
            st.setInt(4, worker.getObjectVersion());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    autoIncKeyId = rs.getLong(1);
                }
            }
            if (autoIncKeyId == -1L) {
                throw new SQLException("can't get iD");
            }
            worker.setDepartmentName("Without department");
            worker.setId(autoIncKeyId);

        } catch (SQLException e) {
            logger.error("Exception when saving worker to MySQL: ", e);
        }
        return worker.getId();
    }

    @Override
    public Worker getById(Long id) {
        String getByIDSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where w.id  = ?";
        Worker worker = null;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByIDSql)) {
            st.setLong(1, id);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    worker = new Worker();
                    worker.setId(res.getLong("id"));
                    worker.setName(res.getString("name"));
                    worker.setLogin(res.getString("login"));
                    worker.setPassword(res.getString("password"));
                    worker.setObjectVersion(res.getInt("objectVersion"));
                    worker.setDepartmentName(res.getString("depName"));
                }
            }

        } catch (SQLException e) {
            logger.error("Exception when get by id:"+worker.getId()+" worker from MySQL: ", e);
        }

        return worker;
    }

    @Override
    public Worker getByName(String name) {
        String getByNameSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where w.name  = ?";
        Worker worker = null;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByNameSql)) {
            st.setString(1, name);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    worker = new Worker();
                    worker.setId(res.getLong("id"));
                    worker.setName(res.getString("name"));
                    worker.setLogin(res.getString("login"));
                    worker.setPassword(res.getString("password"));
                    worker.setObjectVersion(res.getInt("objectVersion"));
                    worker.setDepartmentName(res.getString("depName"));
                }
            }

        } catch (SQLException e) {
            logger.error("Exception when get by name:"+worker.getName()+" worker from MySQL: ", e);
        }

        return worker;
    }

    @Override
    public Worker getByLoginPassword(String login, String pass) {
        String getByLoginPasSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where w.login  = ?" +
                "and w.password = ?";
        Worker worker = null;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByLoginPasSql)) {
            st.setString(1, login);
            st.setString(2, pass);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    worker = new Worker();
                    worker.setId(res.getLong("id"));
                    worker.setName(res.getString("name"));
                    worker.setLogin(res.getString("login"));
                    worker.setPassword(res.getString("password"));
                    worker.setObjectVersion(res.getInt("objectVersion"));
                    worker.setDepartmentName(res.getString("depName"));
                }
            }

        } catch (SQLException e) {
            logger.error("Exception when get login password worker from MySQL: ", e);
        }

        return worker;
    }

    @Override
    public List<Worker> getAll() {
        String getAllSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker";
        List<Worker> workerList = new ArrayList<>();
        try (Connection con = datasource.getConnection();
             Statement st = con.createStatement();
             ResultSet res = st.executeQuery(getAllSql)) {
            while (res.next()) {
                Worker worker = new Worker(res.getLong("id")
                        , res.getString("name")
                        , res.getString("login")
                        , res.getString("password")
                        , res.getInt("objectVersion")
                        , res.getString("depName"));
                workerList.add(worker);

            }

        } catch (SQLException e) {
            logger.error("Exception when get all workers from MySQL: ", e);
        }
        return workerList;
    }

    @Override
    public List<Worker> getByDepartment(Department department) {
        String getByLoginPasSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where dep.id  = ?";
        List<Worker> workerList = new ArrayList<>();
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByLoginPasSql)) {
            st.setLong(1, department.getId());
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    Worker worker = new Worker(res.getLong("id")
                            , res.getString("name")
                            , res.getString("login")
                            , res.getString("password")
                            , res.getInt("objectVersion")
                            , res.getString("depName"));
                    workerList.add(worker);
                }
            }

        } catch (SQLException e) {
            logger.error("Exception when get by department id: "+department.getId()+" worker from MySQL: ", e);
        }

        return workerList;
    }

    @Override
    public boolean remove(Worker worker) {
        if (worker.getId() == null) {
            return false;
        }
        boolean result = false;
        String sqlDelete = "DELETE FROM corporate_messenger.workers WHERE id=?;";

        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(sqlDelete)) {
            st.setLong(1, worker.getId());
            int countRows = st.executeUpdate();
            if (countRows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Exception when remove worker id:"+worker.getId()+" to MySQL: ", e);
        }
        return result;
    }

    @Override
    public boolean update(Worker worker) {
        if (worker.getId() == null) {
            return false;
        }
        boolean result = false;
        String sqlUpdate = "UPDATE corporate_messenger.workers " +
                "SET name= ?, login=?, password=?, objectVersion=? " +
                "WHERE id=? and objectVersion=?;";

        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(sqlUpdate)) {
            int nextObjVersion = worker.getObjectVersion() + 1;
            st.setString(1, worker.getName());
            st.setString(2, worker.getLogin());
            st.setString(3, worker.getPassword());
            st.setInt(4, nextObjVersion);
            st.setLong(5, worker.getId());
            st.setInt(6, worker.getObjectVersion());
            int countRows = st.executeUpdate();
            if (countRows > 0) {
                result = true;
                worker.setObjectVersion(nextObjVersion);
            }
        } catch (SQLException e) {
            logger.error("Exception when update worker id:"+worker.getId()+" to MySQL: ", e);
        }
        return result;
    }

    private static void setPropertiesForDataSource() {
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
