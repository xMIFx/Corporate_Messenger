package com.gitHub.xMIFx.repositories.interfacesDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;

import java.util.List;

/**
 * Created by Vlad on 24.06.2015.
 */
public interface DepartmentDAO {

    //Save department to repositories
    Long saveDepartment(Department department);
    //get department by id
    Department getDepartmentById(Long id);
    //get department by name
    Department getDepartmentByName (String name);
    //get department by worker
    Department getDepartmentByWorker(Worker worker);
    //get All department
     List<Department> getAllDepartments();
    // remove department
    boolean removeDepartment(Department department);
    //update department
    boolean updateDepartment(Department department);
}
