package com.gitHub.xMIFx.repositories.implementationDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Vlad on 24.06.2015.
 */
public class WorkerCollectionDAOImpl implements WorkerDAO {
    private static Map<Long, Worker> workerMap = new HashMap<Long, Worker>();
    private static Long index = 0L;

    public WorkerCollectionDAOImpl() {
    }

    public Long saveWorker(Worker worker) {
        index++;
        worker.setId(index);
        workerMap.put(index, worker);
        return index;
    }

    public Worker getWorkerById(Long id) {
        return workerMap.get(id);
    }

    public Worker getWorkerByName(String name) {
        Worker workerForFind = null;
        for (Map.Entry<Long, Worker> pair : workerMap.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                workerForFind = pair.getValue();
                break;
            }
        }
        return workerForFind;
    }

    public Worker getWorkerByLoginPassword(String login, String pass) {
        return null;
    }

    public List<Worker> getAllWorkers() {
        List<Worker> workerList = new ArrayList<Worker>();
        workerList.addAll(workerMap.values());
        return workerList;
    }

    public List<Worker> getWorkersByDepartment(Department department) {
        return null;
    }

    public boolean removeWorker(Worker worker) {
        if (worker.getId() != null) {
            workerMap.remove(worker.getId());
        }
        return true;
    }

    public boolean updateWorker(Worker worker) {
        return true;
    }


}
