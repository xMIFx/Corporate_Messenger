package com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vlad on 29.06.2015.
 */
public class WorkerJdbcMySQLDAOImpl implements WorkerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerJdbcMySQLDAOImpl.class.getName());
    private static DataSource datasource;

    static {
        datasource = ConnectionPoolTomcat.getDataSource();
    }


    @Override
    public Long save(Worker worker) {
        if (worker.getId() != null) {
            return worker.getId();
        }
        String sqlCreate = "INSERT INTO corporate_messenger.workers " +
                "(name, login, password, objectVersion, admin) " +
                "VALUES (?, ?, ?, ?, ?);";
        Long autoIncKeyId = -1L;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, worker.getName());
            st.setString(2, worker.getLogin());
            st.setString(3, worker.getPassword());
            st.setInt(4, worker.getObjectVersion());
            st.setBoolean(5, worker.isAdmin());
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
            LOGGER.error("Exception when saving worker to MySQL: ", e);
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
                ", w.admin\n" +
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
                    worker.setAdmin(res.getBoolean("admin"));
                }
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when get by id:" + id + " worker from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when get by id:" + id + " worker from MySQL: ", e);
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
                ", w.admin\n" +
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
                    worker.setAdmin(res.getBoolean("admin"));
                }
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when get by name:" + name + " worker from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when get by name:" + name + " worker from MySQL: ", e);
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
                ", w.admin\n" +
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
                    worker.setAdmin(res.getBoolean("admin"));
                }
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when get login password worker from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when get login password worker from MySQL: ", e);
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
                ", w.admin\n" +
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
                worker.setAdmin(res.getBoolean("admin"));
                workerList.add(worker);

            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when when get all workers from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when get all workers from MySQL: ", e);
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
                ", w.admin\n" +
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
                    worker.setAdmin(res.getBoolean("admin"));
                    workerList.add(worker);
                }
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when when get by department id: " + department.getId() + " worker from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when get by department id: " + department.getId() + " worker from MySQL: ", e);
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
            LOGGER.error("Exception when remove worker id:" + worker.getId() + " to MySQL: ", e);
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
            LOGGER.error("Exception when update worker id:" + worker.getId() + " to MySQL: ", e);
        }
        return result;
    }

    @Override
    public List<Worker> findByName(String name) {
        String getByLoginPasSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ", w.admin\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where w.name  LIKE ?";
        List<Worker> workerList = new ArrayList<>();
        String searchValue = "%" + name + "%";
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByLoginPasSql)) {
            st.setString(1, searchValue);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    Worker worker = new Worker(res.getLong("id")
                            , res.getString("name")
                            , res.getString("login")
                            , res.getString("password")
                            , res.getInt("objectVersion")
                            , res.getString("depName"));
                    worker.setAdmin(res.getBoolean("admin"));
                    workerList.add(worker);
                }
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when when when search by name:" + name + " worker from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when search by name: " + name + " worker from MySQL: ", e);
        }

        return workerList;
    }

    @Override
    public List<Worker> findByLogin(String login) {
        String getByLoginPasSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ", w.admin\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where w.login  LIKE ?";
        List<Worker> workerList = new ArrayList<>();
        String searchValue = "%" + login + "%";
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByLoginPasSql)) {
            st.setString(1, searchValue);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    Worker worker = new Worker(res.getLong("id")
                            , res.getString("name")
                            , res.getString("login")
                            , res.getString("password")
                            , res.getInt("objectVersion")
                            , res.getString("depName"));
                    worker.setAdmin(res.getBoolean("admin"));
                    workerList.add(worker);
                }
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when when when search by name:" + login + " worker from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when search by login: " + login + " worker from MySQL: ", e);
        }

        return workerList;
    }

    @Override
    public List<Worker> findByDepartmentName(String depName) {
        String getByLoginPasSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ", w.admin\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where IFNULL(dep.name, \"Without department\")  LIKE ?";
        List<Worker> workerList = new ArrayList<>();
        String searchValue = "%" + depName + "%";
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByLoginPasSql)) {
            st.setString(1, searchValue);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    Worker worker = new Worker(res.getLong("id")
                            , res.getString("name")
                            , res.getString("login")
                            , res.getString("password")
                            , res.getInt("objectVersion")
                            , res.getString("depName"));
                    worker.setAdmin(res.getBoolean("admin"));
                    workerList.add(worker);
                }
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Exception in create worker bean when when when search by name:" + depName + " worker from MySQL: ", e);

        } catch (SQLException e) {
            LOGGER.error("Exception when search by departmentName: " + depName + " worker from MySQL: ", e);
        }

        return workerList;
    }


}
