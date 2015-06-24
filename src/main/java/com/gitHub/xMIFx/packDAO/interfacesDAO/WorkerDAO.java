package com.gitHub.xMIFx.packDAO.interfacesDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;

import java.util.List;

/**
 * Created by Vlad on 24.06.2015.
 */
public interface WorkerDAO {
    //Save worker to repositories
    Long saveWorker(Worker worker);
    //get worker by id
    Worker getWorkerById(Long id);
    //get worker by name
    Worker getWorkerByName (String name);
    //get worker by login and password
    Worker getWorkerByLoginPassword(String login, String pass);
    //get All workers
    List<Worker> getAllWorkers();
    //get workers by department
    List<Worker> getWorkersByDepartment(Department department);
    // remove worker
    boolean removeWorker(Worker worker);
    //update worker
    boolean updateWorker(Worker worker);

}
