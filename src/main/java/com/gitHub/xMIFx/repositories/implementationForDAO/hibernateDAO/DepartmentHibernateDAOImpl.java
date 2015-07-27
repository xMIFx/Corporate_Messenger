package com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;

import java.util.List;

/**
 * Created by Vlad on 27.07.2015.
 */
public class DepartmentHibernateDAOImpl implements DepartmentDAO {
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
    public List<Department> getAllWithoutWorkers() {
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

    @Override
    public List<Long> getForUpdateByWorkers(List<Worker> workerList) {
        return null;
    }

    @Override
    public List<Department> getForUpdateByID(List<Long> listID) {
        return null;
    }

    @Override
    public List<Department> findByName(String name) {
        return null;
    }
}
