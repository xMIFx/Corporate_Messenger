package com.gitHub.xMIFx.packDAO.implementationDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.packDAO.interfacesDAO.DepartmentDAO;

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

    public Long saveDepartment(Department department) {
        index++;
        department.setId(index);
        departmentMap.put(index, department);
        return index;
    }

    public Department getDepartmentById(Long id) {
        return departmentMap.get(id);
    }

    public Department getDepartmentByName(String name) {
        Department departmentForFind = null;
        for (Map.Entry<Long, Department> pair : departmentMap.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                departmentForFind = pair.getValue();
                break;
            }
        }
        return departmentForFind;
    }

    public Department getDepartmentByWorker(Worker worker) {
        Department departmentForFind = null;
        for (Map.Entry<Long, Department> pair : departmentMap.entrySet()) {

            if (pair.getValue().getWorkers().contains(worker)) {
                departmentForFind = pair.getValue();
                break;
            }
        }

    return departmentForFind;
}

    public List<Department> getAllDepartments() {
        List<Department> departmentList = new ArrayList<Department>();
        departmentList.addAll(departmentMap.values());
        return departmentList;
    }

    public boolean removeDepartment(Department department) {
        if (department.getId() != null) {
            departmentMap.remove(department.getId());
        }
        return true;
    }

    public boolean updateDepartment(Department department) {
        return true;
    }
}
