package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.dto.WorkersHolder;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 11.07.2015.
 */
public class WorkerServiceImpl extends MainServiceImpl implements WorkerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerServiceImpl.class.getName());
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();
    private static WorkerDAO workerDAO = abstractFactoryForDAOf.getWorkersDAOImpl();



    @Override
    public String find(FinderType finderType, String searchValue) {
        String answer = null;
        if (searchValue == null || "".equals(searchValue.trim())) {
            answer = getAllAnswer();
        }
        List<Worker> workerList = null;
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
        WorkersHolder workersHolder = new WorkersHolder();
        workersHolder.setWorkers(workerList);
        try {
            answer = getXMLMessage(workersHolder);
        } catch (JAXBException e) {
            LOGGER.error("some jaxB exception: ", e);
        }
        return answer;
    }

    @Override
    public String getAllAnswer() {
        String answer = null;
        List<Worker> workerList = workerDAO.getAll();
        WorkersHolder workersHolder = new WorkersHolder();
        workersHolder.setWorkers(workerList);
        try {
            answer = getXMLMessage(workersHolder);
        } catch (JAXBException e) {
            LOGGER.error("some jaxB exception: ", e);
        }
        return answer;
    }

    @Override
    public String create(String name, String login, String password) {
        String answer = null;

        try {
            try {
                Worker worker = new Worker(null, name, login, password);
                if (workerDAO.save(worker) == null) {
                    ExceptionForView exceptionForView = new ExceptionForView();
                    exceptionForView.setExceptionMessage("Error when saving. Try later.");
                    answer = getXMLMessage(exceptionForView);
                } else {
                    answer = getXMLMessage(worker);
                }
            } catch (IllegalArgumentException e) {
                LOGGER.error("wrong format in name: " + name + " or login: " + login + " or pas: " + password, e);
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Wrong format in name or login or password.");
                answer = getXMLMessage(exceptionForView);
            }
        } catch (JAXBException e) {
            LOGGER.error("some jaxB exception: ", e);
        }

        return answer;
    }

    @Override
    public String update(Long id, String name, String login, String password, int objVersion, String depName) {
        String answer = null;

        try {
            try {
                Worker worker = new Worker(id, name, login, password, objVersion, depName);
                if (!workerDAO.update(worker)) {
                    ExceptionForView exceptionForView = new ExceptionForView();
                    exceptionForView.setExceptionMessage("Error when saving. Try later.");

                    answer = getXMLMessage(exceptionForView);

                } else {
                    answer = getXMLMessage(worker);
                }
            } catch (IllegalArgumentException e) {
                LOGGER.error("wrong format in name: " + name + " or login: " + login + " or pas: " + password, e);
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Wrong format in name or login or password.");
                answer = getXMLMessage(exceptionForView);
            }
        } catch (JAXBException e) {
            LOGGER.error("some jaxB exception: ", e);
        }
        return answer;
    }

    @Override
    public String getByIDAnswer(Long id) {
        String answer = null;
        Worker worker = workerDAO.getById(id);
        try {
            answer = getXMLMessage(worker);
        } catch (JAXBException e) {
            LOGGER.error("some jaxB exception: ", e);
        }
        return answer;
    }

    @Override
    public String deleteByID(Long id) {
        String answer = null;
        Worker worker = workerDAO.getById(id);
        try {
            if (!workerDAO.remove(worker)) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when delete. Try later.");
                worker = null;
                answer = getXMLMessage(exceptionForView);
            } else {
                answer = getXMLMessage(worker);
            }
        } catch (JAXBException e) {
            LOGGER.error("some jaxB exception: ", e);
        }
        return answer;
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
            worker  = workerDAO.getById(id);
        }
        return worker;
    }

}
