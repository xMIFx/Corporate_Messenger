package com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 29.06.2015.
 */
public class DepartmentJdbcMySQLDAOImpl implements DepartmentDAO {
    private static final Logger logger = LoggerFactory.getLogger(WorkerJdbcMySQLDAOImpl.class.getName());
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/testBase1_0?user=root&password=Lytghj12";
    private static javax.sql.DataSource datasource;

    static {
        datasource = ConnectionPoolTomcat.getDataSource();
    }


    @Override
    public Long save(Department department) {
        if (department.getId() != null) {
            return department.getId();
        }
        String sqlCreate = "INSERT INTO corporate_messenger.departments" +
                "(name, objectVersion) " +
                "VALUES (?, ?);";
        String sqlDeleteBindingWorker = "DELETE FROM corporate_messenger.departmentworkers WHERE idworker=?;";
        String sqlAddBindingWorkerDepartment = "INSERT INTO corporate_messenger.departmentworkers " +
                "(idworker, iddepartment) " +
                "VALUES (?, ?);";
        Long autoIncKeyId = -1L;
        final int batchSize = 1000;
        try (Connection con = datasource.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement st = con.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, department.getName());
                st.setInt(2, department.getObjectVersion());
                st.executeUpdate();
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        autoIncKeyId = rs.getLong(1);
                    }
                }
                if (autoIncKeyId == -1L) {
                    throw new SQLException("can't get iD");
                }

                //first: delete binding worker-department for new workers
                if (department.getWorkers().size() > 0) {
                    try (PreparedStatement stForWorkerBindingDelete = con.prepareStatement(sqlDeleteBindingWorker)) {
                        int count = 0;
                        for (Worker worker : department.getWorkers()) {
                            stForWorkerBindingDelete.setLong(1, worker.getId());
                            stForWorkerBindingDelete.addBatch();
                            if (++count % batchSize == 0) {
                                stForWorkerBindingDelete.executeBatch();
                            }
                        }
                        stForWorkerBindingDelete.executeBatch();
                    }
                    //second: add to binding workerDepartment all workers
                    try (PreparedStatement stForAddDepartmentBinding = con.prepareStatement(sqlAddBindingWorkerDepartment)) {
                        int count = 0;
                        for (Worker worker : department.getWorkers()) {
                            stForAddDepartmentBinding.setLong(1, worker.getId());
                            stForAddDepartmentBinding.setLong(2, autoIncKeyId);
                            stForAddDepartmentBinding.addBatch();
                            if (++count % batchSize == 0) {
                                stForAddDepartmentBinding.executeBatch();
                            }
                        }
                        stForAddDepartmentBinding.executeBatch();
                    }
                }
                con.commit();
                department.setId(autoIncKeyId);

            } catch (SQLException e) {
                logger.error("Exception when saving department to MySQL: ", e);
                con.rollback();
            }
        } catch (SQLException e) {
            logger.error("Exception when saving department to MySQL: ", e);
        }
        return department.getId();
    }

    @Override
    public Department getById(Long id) {
        String getByIDSql = "SELECT \n" +
                "dep.id\n" +
                ", dep.name \n" +
                ", dep.objectVersion\n" +
                ", depWork.idworker \n" +
                ", w.name workerName\n" +
                ", w.login\n" +
                ", w.objectVersion workerObjVersion\n" +
                "\n" +
                " FROM corporate_messenger.departments dep\n" +
                "\tleft join departmentworkers depWork\n" +
                "\t\tleft join  workers w\n" +
                "        on depwork.idworker = w.id\n" +
                "\ton dep.id = depWork.iddepartment\n" +
                " where dep.id = ?";
        Department department = null;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByIDSql)) {
            st.setLong(1, id);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    if (department == null) {
                        department = new Department(res.getString("name"));
                        department.setId(res.getLong("id"));
                        department.setObjectVersion(res.getInt("objectVersion"));

                    }
                    Long idWorker = res.getLong("idworker");
                    if (!res.wasNull()) {
                        Worker worker = new Worker();
                        worker.setId(idWorker);
                        worker.setName(res.getString("workerName"));
                        worker.setLogin(res.getString("login"));
                        worker.setObjectVersion(res.getInt("workerObjVersion"));
                        department.addWorker(worker);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Exception in create department bean when get by id:" + id + " worker from MySQL: ", e);

        } catch (SQLException e) {
            logger.error("Exception when get by id:" + id + " worker from MySQL: ", e);
        }

        return department;
    }

    @Override
    public Department getByName(String name) {
        String getByNameSql = "SELECT \n" +
                "dep.id\n" +
                ", dep.name \n" +
                ", dep.objectVersion\n" +
                ", depWork.idworker \n" +
                ", w.name workerName\n" +
                ", w.login\n" +
                ", w.objectVersion workerObjVersion\n" +
                "\n" +
                " FROM corporate_messenger.departments dep\n" +
                "\tleft join departmentworkers depWork\n" +
                "\t\tleft join  workers w\n" +
                "        on depwork.idworker = w.id\n" +
                "\ton dep.id = depWork.iddepartment\n" +
                " where dep.name = ?";
        Department department = null;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByNameSql)) {
            st.setString(1, name);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    if (department == null) {
                        department = new Department(res.getString("name"));
                        department.setId(res.getLong("id"));
                        department.setObjectVersion(res.getInt("objectVersion"));

                    }
                    Long idWorker = res.getLong("idworker");
                    if (!res.wasNull()) {
                        Worker worker = new Worker();
                        worker.setId(idWorker);
                        worker.setName(res.getString("workerName"));
                        worker.setLogin(res.getString("login"));
                        worker.setObjectVersion(res.getInt("workerObjVersion"));
                        department.addWorker(worker);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Exception in create department bean when get by name:" + name + " worker from MySQL: ", e);

        } catch (SQLException e) {
            logger.error("Exception when get by name:" + name + " worker from MySQL: ", e);
        }

        return department;
    }

    @Override
    public Department getByWorker(Worker worker) {
        String getByWorkerSql = "SELECT \n" +
                "dep.id\n" +
                ", dep.name \n" +
                ", dep.objectVersion\n" +
                ", depWork.idworker \n" +
                ", w.name workerName\n" +
                ", w.login\n" +
                ", w.objectVersion workerObjVersion\n" +
                "\n" +
                " FROM corporate_messenger.departments dep\n" +
                "\tleft join departmentworkers depWork\n" +
                "\t\tleft join  workers w\n" +
                "        on depwork.idworker = w.id\n" +
                "\ton dep.id = depWork.iddepartment\n" +
                " where depWork.idworker = ?";
        Department department = null;
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(getByWorkerSql)) {
            st.setLong(1, worker.getId());
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    if (department == null) {
                        department = new Department(res.getString("name"));
                        department.setId(res.getLong("id"));
                        department.setObjectVersion(res.getInt("objectVersion"));

                    }
                    Long idWorker = res.getLong("idworker");
                    if (!res.wasNull()) {
                        Worker newWorker = new Worker();
                        newWorker.setId(idWorker);
                        newWorker.setName(res.getString("workerName"));
                        newWorker.setLogin(res.getString("login"));
                        newWorker.setObjectVersion(res.getInt("workerObjVersion"));
                        department.addWorker(newWorker);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Exception in create department bean when get by worker:" + worker + " worker from MySQL: ", e);

        } catch (SQLException e) {
            logger.error("Exception when get by worker:" + worker + " worker from MySQL: ", e);
        }

        return department;
    }

    @Override
    public List<Department> getAll() {
        String getAllSql = "SELECT \n" +
                "dep.id\n" +
                ", dep.name \n" +
                ", dep.objectVersion\n" +
                ", depWork.idworker \n" +
                ", w.name workerName\n" +
                ", w.login\n" +
                ", w.objectVersion workerObjVersion\n" +
                "\n" +
                " FROM corporate_messenger.departments dep\n" +
                "\tleft join departmentworkers depWork\n" +
                "\t\tleft join  workers w\n" +
                "        on depwork.idworker = w.id\n" +
                "\ton dep.id = depWork.iddepartment\n" +
                "   \n";
        Map<Long, Department> departmentMap = new HashMap<>();
        try (Connection con = datasource.getConnection();
             Statement st = con.createStatement();
             ResultSet res = st.executeQuery(getAllSql)) {
            while (res.next()) {
                Long departmentID = res.getLong("id");
                Department department;

                if (departmentMap.containsKey(departmentID)) {
                    department = departmentMap.get(departmentID);
                } else {
                    department = new Department(res.getString("name"));
                    department.setId(departmentID);
                    department.setObjectVersion(res.getInt("objectVersion"));
                    departmentMap.put(departmentID, department);
                }
                Long idWorker = res.getLong("idworker");
                if (!res.wasNull()) {
                    Worker worker = new Worker();
                    worker.setId(idWorker);
                    worker.setName(res.getString("workerName"));
                    worker.setLogin(res.getString("login"));
                    worker.setObjectVersion(res.getInt("workerObjVersion"));
                    department.addWorker(worker);
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Exception when create department bean get all department from MySQL: ", e);
        } catch (SQLException e) {
            logger.error("Exception when get all department from MySQL: ", e);
        }
        List<Department> departmentList = new ArrayList<>();
        departmentList.addAll(departmentMap.values());
        return departmentList;
    }

    @Override
    public List<Department> getAllWithoutWorkers() {
        String getAllSql = "SELECT \n" +
                "dep.id\n" +
                ", dep.name\n" +
                ", dep.objectVersion\n" +
                ", Count(depWork.idworker) countWorkers\n" +
                " FROM corporate_messenger.departments dep\n" +
                "\tleft join departmentworkers depWork\n" +
                "\ton dep.id = depWork.iddepartment\n" +
                " group by dep.id\n" +
                ", dep.name\n" +
                ", dep.objectVersion";
        List<Department> departmentList = new ArrayList<>();
        try (Connection con = datasource.getConnection();
             Statement st = con.createStatement();
             ResultSet res = st.executeQuery(getAllSql)) {
            while (res.next()) {
                Department department = new Department(res.getString("name"));
                department.setId(res.getLong("id"));
                department.setObjectVersion(res.getInt("objectVersion"));
                department.setWorkersCount(res.getInt("countWorkers"));
                departmentList.add(department);
            }
        } catch (SQLException e) {
            logger.error("Exception when get all department from MySQL: ", e);
        }
        return departmentList;
    }

    @Override
    public boolean remove(Department department) {
        if (department.getId() == null) {
            return false;
        }
        boolean result = false;
        String sqlDelete = "DELETE FROM corporate_messenger.departments WHERE id=?;";

        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(sqlDelete)) {
            st.setLong(1, department.getId());
            int countRows = st.executeUpdate();
            if (countRows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            logger.error("Exception when remove department id:" + department.getId() + " to MySQL: ", e);
        }
        return result;
    }

    @Override
    public boolean update(Department department) {
        if (department.getId() == null) {
            return false;
        }
        boolean result = false;
        String sqlUpdate = "UPDATE corporate_messenger.departments " +
                "SET name=?, objectVersion=? " +
                "WHERE id=? and objectVersion=?;";

        String sqlDeleteBindingWorker = "DELETE FROM corporate_messenger.departmentworkers WHERE idworker=?;";
        String sqlDeleteBindingDepartment = "DELETE FROM corporate_messenger.departmentworkers WHERE iddepartment=?;";
        String sqlAddBindingWorkerDepartment = "INSERT INTO corporate_messenger.departmentworkers " +
                "(idworker, iddepartment) " +
                "VALUES (?, ?);";
        final int batchSize = 1000;
        try (Connection con = datasource.getConnection()) {
            try (PreparedStatement st = con.prepareStatement(sqlUpdate)) {
                con.setAutoCommit(false);
                int nextObjVersion = department.getObjectVersion() + 1;
                st.setString(1, department.getName());
                st.setInt(2, nextObjVersion);
                st.setLong(3, department.getId());
                st.setInt(4, department.getObjectVersion());
                int countRows = st.executeUpdate();
                if (countRows > 0) {
                    result = true;
                    department.setObjectVersion(nextObjVersion);
                }
                // first: delete all workers from department
                try (PreparedStatement stForDepartmentBindingDelete = con.prepareStatement(sqlDeleteBindingDepartment)) {
                    stForDepartmentBindingDelete.setLong(1, department.getId());
                    stForDepartmentBindingDelete.executeUpdate();
                }
                //second: delete binding worker-department for new workers
                if (department.getWorkers().size() > 0) {
                    try (PreparedStatement stForWorkerBindingDelete = con.prepareStatement(sqlDeleteBindingWorker)) {
                        int count = 0;
                        for (Worker worker : department.getWorkers()) {
                            stForWorkerBindingDelete.setLong(1, worker.getId());
                            stForWorkerBindingDelete.addBatch();
                            if (++count % batchSize == 0) {
                                stForWorkerBindingDelete.executeBatch();
                            }
                        }
                        stForWorkerBindingDelete.executeBatch();
                    }
                    //third: add to binding workerDepartment all workers
                    try (PreparedStatement stForAddDepartmentBinding = con.prepareStatement(sqlAddBindingWorkerDepartment)) {
                        int count = 0;
                        for (Worker worker : department.getWorkers()) {
                            stForAddDepartmentBinding.setLong(1, worker.getId());
                            stForAddDepartmentBinding.setLong(2, department.getId());
                            stForAddDepartmentBinding.addBatch();
                            if (++count % batchSize == 0) {
                                stForAddDepartmentBinding.executeBatch();
                            }
                        }
                        stForAddDepartmentBinding.executeBatch();
                    }
                }
                con.commit();
                con.close();
            } catch (SQLException e) {
                logger.error("Exception when update department id:" + department.getId() + " to MySQL: ", e);
                con.rollback();
                result = false;
            }
        } catch (SQLException e) {
            logger.error("Exception when getting connection by department id:" + department.getId() + " to MySQL: ", e);
        }
        return result;
    }

    @Override
    public List<Department> findByName(String name) {
        String findByNameSql = "SELECT \n" +
                "dep.id\n" +
                ", dep.name \n" +
                ", dep.objectVersion\n" +
                ", depWork.idworker \n" +
                ", w.name workerName\n" +
                ", w.login\n" +
                ", w.objectVersion workerObjVersion\n" +
                "\n" +
                " FROM corporate_messenger.departments dep\n" +
                "\tleft join departmentworkers depWork\n" +
                "\t\tleft join  workers w\n" +
                "        on depwork.idworker = w.id\n" +
                "\ton dep.id = depWork.iddepartment\n" +
                " where dep.name LIKE ?";
        String searchValue = "%" + name + "%";
        Map<Long, Department> departmentMap = new HashMap<>();
        try (Connection con = datasource.getConnection();
             PreparedStatement st = con.prepareStatement(findByNameSql)) {
            st.setString(1, searchValue);
            try (ResultSet res = st.executeQuery()) {
                while (res.next()) {
                    Long departmentID = res.getLong("id");
                    Department department;

                    if (departmentMap.containsKey(departmentID)) {
                        department = departmentMap.get(departmentID);
                    } else {
                        department = new Department(res.getString("name"));
                        department.setId(departmentID);
                        department.setObjectVersion(res.getInt("objectVersion"));
                        departmentMap.put(departmentID, department);
                    }
                    Long idWorker = res.getLong("idworker");
                    if (!res.wasNull()) {
                        Worker worker = new Worker();
                        worker.setId(idWorker);
                        worker.setName(res.getString("workerName"));
                        worker.setLogin(res.getString("login"));
                        worker.setObjectVersion(res.getInt("workerObjVersion"));
                        department.addWorker(worker);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Exception when get all department from MySQL: ", e);
        } catch (IllegalArgumentException e) {
            logger.error("Exception when get all department from MySQL: ", e);
        }
        List<Department> departmentList = new ArrayList<>();
        departmentList.addAll(departmentMap.values());
        return departmentList;
    }
}
