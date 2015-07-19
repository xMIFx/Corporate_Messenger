package com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 24.06.2015.
 */
public class DepartmentCollectionDAOImpl implements DepartmentDAO {
    private static Map<Long, Department> departmentMap = new HashMap<Long, Department>();
    private static Long index = 0L;

    public DepartmentCollectionDAOImpl() {

    }

    private static synchronized Long increaseIndex() {
        return ++index;
    }

    public Long save(Department department) {
        department.setId(increaseIndex());
        departmentMap.put(index, department);
        return index;
    }

    public Department getById(Long id) {
        return departmentMap.get(id);
    }

    public Department getByName(String name) {
        Department departmentForFind = null;
        for (Map.Entry<Long, Department> pair : departmentMap.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                departmentForFind = pair.getValue();
                break;
            }
        }
        return departmentForFind;
    }

    public Department getByWorker(Worker worker) {
        Department departmentForFind = null;
        for (Map.Entry<Long, Department> pair : departmentMap.entrySet()) {

            if (pair.getValue().getWorkers().contains(worker)) {
                departmentForFind = pair.getValue();
                break;
            }
        }

        return departmentForFind;
    }

    public List<Department> getAll() {
        List<Department> departmentList = new ArrayList<Department>();
        departmentList.addAll(departmentMap.values());
        return departmentList;
    }

    @Override
    public List<Department> getAllWithoutWorkers() {
        return null;
    }

    public boolean remove(Department department) {
        if (department.getId() != null) {
            departmentMap.remove(department.getId());
        }
        return true;
    }

    public boolean update(Department department) {
        return true;
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
