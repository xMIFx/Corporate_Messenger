package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.realisationForDTO.WorkersHolder;
import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.implementationServices.ConverterObjectToStringXML;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.ConverterObjectToString;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Vlad on 21.07.2015.
 */
@Component
public class RecipientOfResponseForWorker {
    static final Logger LOGGER = LoggerFactory.getLogger(RecipientOfResponseForWorker.class.getName());
    private static final ConverterObjectToString CONVERTER_OBJECT_TO_STRING = new ConverterObjectToStringXML();

    @Autowired
    @Qualifier("workerService")
    private WorkerService workerService;

    String find(FinderType finderType, String searchValue) {
        String answer = null;

        List<Worker> workerList = workerService.find(finderType, searchValue);

        WorkersHolder workersHolder = new WorkersHolder();
        workersHolder.setWorkers(workerList);

        answer =  CONVERTER_OBJECT_TO_STRING.getMessage(workersHolder);

        return answer;
    }

     String getAll() {
        String answer = null;
        List<Worker> workerList = workerService.getAll();
        WorkersHolder workersHolder = new WorkersHolder();
        workersHolder.setWorkers(workerList);
        answer =  CONVERTER_OBJECT_TO_STRING.getMessage(workersHolder);
        return answer;
    }

     String create(String name, String login, String password) {
        String answer = null;

        try {
            Worker worker = new Worker(null, name, login, password);
            if (workerService.create(worker) == null) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when saving. Try later.");
                answer =  CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
            } else {
                answer =  CONVERTER_OBJECT_TO_STRING.getMessage(worker);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("wrong format in name: " + name + " or login: " + login + " or pas: " + password, e);
            ExceptionForView exceptionForView = new ExceptionForView();
            exceptionForView.setExceptionMessage("Wrong format in name or login or password.");
            answer =  CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        }

        return answer;
    }

     String update(Long id, String name, String login, String password, int objVersion, String depName) {
        String answer = null;

        try {
            Worker worker = new Worker(id, name, login, password, objVersion, depName);
            if (!workerService.update(worker)) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when saving. Try later.");
                answer =  CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
            } else {
                answer =  CONVERTER_OBJECT_TO_STRING.getMessage(worker);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("wrong format in name: " + name + " or login: " + login + " or pas: " + password, e);
            ExceptionForView exceptionForView = new ExceptionForView();
            exceptionForView.setExceptionMessage("Wrong format in name or login or password.");
            answer =  CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        }

        return answer;
    }

     String getByID(Long id) {
        String answer = null;
        Worker worker = workerService.getByID(id);
        answer =  CONVERTER_OBJECT_TO_STRING.getMessage(worker);
        return answer;
    }

     String deleteByID(Long id) {
        String answer = null;
        Worker worker = workerService.getByID(id);
        if (!workerService.delete(worker)) {
            ExceptionForView exceptionForView = new ExceptionForView();
            exceptionForView.setExceptionMessage("Error when delete. Try later.");
            worker = null;
            answer =  CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        } else {
            answer =  CONVERTER_OBJECT_TO_STRING.getMessage(worker);
        }
        return answer;
    }

     String getAnswerAboutException(String exceptionMessage) {
        String answer = null;
        ExceptionForView exceptionForView = new ExceptionForView();
        exceptionForView.setExceptionMessage(exceptionMessage);
        answer =  CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        return answer;
    }

}
