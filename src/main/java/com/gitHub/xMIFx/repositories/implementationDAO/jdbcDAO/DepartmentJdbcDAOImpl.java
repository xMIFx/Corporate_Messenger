package com.gitHub.xMIFx.repositories.implementationDAO.jdbcDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;

import java.util.List;

/**
 * Created by Vlad on 29.06.2015.
 */
public class DepartmentJdbcDAOImpl implements DepartmentDAO {
    @Override
    public Long save(Department department) {
        return null;
    }

    @Override
    public Department getById(Long id) {
        return null;
    }

    @Override
    public Department getByName(String name) {
        return null;
    }

    @Override
    public Department getByWorker(Worker worker) {
        return null;
    }

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public boolean remove(Department department) {
        return false;
    }

    @Override
    public boolean update(Department department) {
        return false;
    }
}
