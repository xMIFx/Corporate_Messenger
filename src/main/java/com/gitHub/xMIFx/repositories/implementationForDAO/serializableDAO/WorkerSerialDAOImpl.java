package com.gitHub.xMIFx.repositories.implementationForDAO.serializableDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.projectConfig.PropertiesForWork;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerSerialDAOImpl implements WorkerDAO {
    private static String filePath = PropertiesForWork.getPropertiesForWork().getPathToWorkers();
    private static final Logger logger = LoggerFactory.getLogger(WorkerSerialDAOImpl.class.getName());
    // we need to clone object when someone getting it
    private static Map<Long, Worker> workerMap = new HashMap<Long, Worker>();
    private static Long index = 0L;

    static {
        try {
            readAllObject();
        } catch (IOException e) {
            logger.error("Can't read file: ", e);
        }
    }

    public WorkerSerialDAOImpl() {
    }

    private static synchronized Long increaseIndex() {
        return ++index;
    }

    public Long save(Worker worker) {

        worker.setId(increaseIndex());
        workerMap.put(index, worker);
        try {
            saveAllObject();
        } catch (IOException e) {
            workerMap.remove(index);
            logger.error("exception when saving: ", e);
            worker.setId(null);
            return null;
        }
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
        boolean allIsOK = true;
        if (worker.getId() != null) {
            Worker oldValue = workerMap.get(worker.getId());
            workerMap.remove(worker.getId());
            try {
                saveAllObject();
            } catch (IOException e) {
                workerMap.put(oldValue.getId(), oldValue);
                allIsOK = false;
                logger.error("exception when removing: ", e);
            }
        }
        return allIsOK;
    }

    public boolean update(Worker worker) {
        //some code for serialize all Department, even we update one
        boolean allIsOK = true;

        if (worker.getId() != null) {
            Worker oldValue = workerMap.get(worker.getId());
            workerMap.put(worker.getId(), worker);
            try {
                saveAllObject();
            } catch (IOException e) {
                allIsOK = false;
                workerMap.put(oldValue.getId(), oldValue);
                logger.error("exception when update: ", e);
            }
        } else {
            allIsOK = false;
        }
        return allIsOK;
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

    private static void readAllObject() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                Worker readWorker = new Worker();
                try {
                    readWorker.readExternal(in);
                } catch (ClassNotFoundException e) {
                    logger.error("Can't read some dep from file: ", e);
                }
                if (readWorker.getId() != null) {
                    workerMap.put(readWorker.getId(), readWorker);
                    if (index < readWorker.getId()) {
                        index = readWorker.getId();
                    } else {
                        /*NOP*/
                    }
                } else {
                    throw new IOException("some problem with id");
                }
            }

        }
        System.out.println(workerMap.values().toString());
    }

    private boolean saveAllObject() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
            out.writeInt(workerMap.size());
            for (Map.Entry<Long, Worker> pair : workerMap.entrySet()) {
                pair.getValue().writeExternal(out);
            }

        }
        return true;
    }

}
