package com.gitHub.xMIFx.repositories.interfacesDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;

import java.util.List;

/**
 * Created by Vlad on 24.06.2015.
 */
public interface WorkerDAO {
    //Save worker to repositories
    Long save(Worker worker);
    //get worker by id
    Worker getById(Long id);
    //get worker by name
    Worker getByName(String name);
    //get worker by login and password
    Worker getByLoginPassword(String login, String pass);
    //get All workers
    List<Worker> getAll();
    //get workers by department
    List<Worker> getByDepartment(Department department);
    // remove worker
    boolean remove(Worker worker);
    //update worker
    boolean update(Worker worker);

}
