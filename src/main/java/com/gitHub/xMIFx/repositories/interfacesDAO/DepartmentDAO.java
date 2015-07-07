package com.gitHub.xMIFx.repositories.interfacesDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;

import java.util.List;

/**
 * Created by Vlad on 24.06.2015.
 */
public interface DepartmentDAO {

    //Save department to repositories
    Long save(Department department);
    //get department by id
    Department getById(Long id);
    //get department by name
    Department getByName(String name);
    //get department by worker
    Department getByWorker(Worker worker);
    //get All department
     List<Department> getAll();

    List<Department> getAllWithoutWorkers();
    // remove department
    boolean remove(Department department);
    //update department
    boolean update(Department department);
}
