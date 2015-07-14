package com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO;

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

    private static synchronized Long increaseIndex() {
        return ++index;
    }

    public Long save(Worker worker) {

        worker.setId(increaseIndex());
        workerMap.put(index, worker);
        return index;
    }

    public Worker getById(Long id) {
        return workerMap.get(id);
    }

    public Worker getByName(String name) {
        Worker workerForFind = null;
        for (Map.Entry<Long, Worker> pair : workerMap.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                workerForFind = pair.getValue();
                break;
            }
        }
        return workerForFind;
    }

    public Worker getByLoginPassword(String login, String pass) {
        return null;
    }

    public List<Worker> getAll() {
        List<Worker> workerList = new ArrayList<Worker>();
        workerList.addAll(workerMap.values());
        return workerList;
    }

    public List<Worker> getByDepartment(Department department) {
        return null;
    }

    public boolean remove(Worker worker) {
        if (worker.getId() != null) {
            workerMap.remove(worker.getId());
        }
        return true;
    }

    public boolean update(Worker worker) {
        return true;
    }

    @Override
    public List<Worker> findByName(String name) {
        return null;
    }

    @Override
    public List<Worker> findByLogin(String login) {
        return null;
    }

    @Override
    public List<Worker> findByDepartmentName(String depName) {
        return null;
    }


}
