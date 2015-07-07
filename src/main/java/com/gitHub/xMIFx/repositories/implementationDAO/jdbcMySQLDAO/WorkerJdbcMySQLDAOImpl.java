package com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 29.06.2015.
 */
public class WorkerJdbcMySQLDAOImpl implements WorkerDAO {
    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/testBase1_0?user=root&password=Lytghj12";


    @Override
    public Long save(Worker worker) {
        try (Connection con = DriverManager.getConnection(JDBC_URL)) {
        } catch (SQLException e) {
            e.printStackTrace();
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
}
