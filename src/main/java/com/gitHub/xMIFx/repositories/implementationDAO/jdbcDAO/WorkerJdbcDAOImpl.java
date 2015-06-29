package com.gitHub.xMIFx.repositories.implementationDAO.jdbcDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

import java.util.List;

/**
 * Created by Vlad on 29.06.2015.
 */
public class WorkerJdbcDAOImpl implements WorkerDAO{
    @Override
    public Long save(Worker worker) {
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
