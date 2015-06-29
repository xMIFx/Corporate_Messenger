package com.gitHub.xMIFx.repositories.implementationDAO.jsonDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.projectConfig.PropertiesForWork;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 29.06.2015.
 */
public class WorkerJsonDAOImpl implements WorkerDAO {
    private static String filePath = PropertiesForWork.getPropertiesForWork().getPathToWorkers();
    private static final Logger logger = LoggerFactory.getLogger(WorkerJsonDAOImpl.class.getName());
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

    public WorkerJsonDAOImpl() {
    }

    public Long save(Worker worker) {
        index++;
        worker.setId(index);
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

    private static void readAllObject() throws IOException {

        File file = new File(filePath);
        JAXBContext jaxbContext = null;

        ObjectMapper mapper = new ObjectMapper();
        WorkersHolder workersHolder = mapper.readValue(file, WorkersHolder.class);
        ;
        List<Worker> workerList = workersHolder.getWorkers();
        for (Worker worker : workerList) {
            workerMap.put(worker.getId(), worker);
            if (index < worker.getId()) {
                index = worker.getId();
            }
        }
        System.out.println(workerMap.values().toString());
    }

    private boolean saveAllObject() throws IOException {
        File file = new File(filePath);

        List<Worker> workerList = new ArrayList<>();
        workerList.addAll(workerMap.values());
        WorkersHolder workersHolder = new WorkersHolder();
        workersHolder.setWorkers(workerList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, workersHolder);

        return true;
    }


    private static class WorkersHolder {
        private List<Worker> workers;

        public List<Worker> getWorkers() {
            return workers;
        }

        public void setWorkers(List<Worker> workers) {
            this.workers = workers;
        }
    }

}
