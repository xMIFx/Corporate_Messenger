package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 11.07.2015.
 */
public class WorkerServiceImpl implements WorkerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerServiceImpl.class.getName());
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();
    private static WorkerDAO workerDAO = abstractFactoryForDAOf.getWorkersDAOImpl();


    @Override
    public List<Worker> find(FinderType finderType, String searchValue) {
        List<Worker> workerList = null;
        if (searchValue == null || "".equals(searchValue.trim())) {
            workerList = getAll();
        } else {
            switch (finderType) {
                case NAME:
                    workerList = workerDAO.findByName(searchValue);
                    break;
                case LOGIN:
                    workerList = workerDAO.findByLogin(searchValue);
                    break;
                case DEPARTMENT:
                    workerList = workerDAO.findByDepartmentName(searchValue);
                    break;
                default:
                    workerList = new ArrayList<>();
                    break;
            }
        }

        return workerList;
    }

    @Override
    public List<Worker> getAll() {
        List<Worker> workerList = workerDAO.getAll();
        return workerList;
    }

    @Override
    public Long create(Worker worker) {
        Long id = workerDAO.save(worker);
        return id;
    }

    @Override
    public boolean update(Worker worker) {
        return workerDAO.update(worker);
    }


    @Override
    public boolean delete(Worker worker) {
        return workerDAO.remove(worker);
    }

    @Override
    public Worker getByLoginPassword(String login, String password) {
        Worker worker = null;
        if (login != null && password != null) {
            worker = workerDAO.getByLoginPassword(login, password);
        } else {/*NOP*/}
        return worker;
    }

    @Override
    public Worker getByID(Long id) {
        Worker worker = null;
        if (id != null) {
            worker = workerDAO.getById(id);
        }
        return worker;
    }

}
