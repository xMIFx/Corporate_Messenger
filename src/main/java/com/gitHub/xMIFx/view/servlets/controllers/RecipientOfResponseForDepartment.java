package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.repositories.realisationForDTO.DepartmentsHolder;
import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.implementationServices.ConverterObjectToStringXML;
import com.gitHub.xMIFx.services.implementationServices.DepartmentServiceImpl;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.ConverterObjectToString;
import com.gitHub.xMIFx.services.interfaces.DepartmentService;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RecipientOfResponseForDepartment {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipientOfResponseForDepartment.class.getName());
    private static final ConverterObjectToString CONVERTER_OBJECT_TO_STRING = new ConverterObjectToStringXML();

    @Autowired
    @Qualifier("departmentService")
    private DepartmentService departmentService;

    String find(FinderType finderType, String searchValue) {
        String answer = null;
        List<Department> departmentList = departmentService.find(finderType, searchValue);
        DepartmentsHolder departmentsHolder = new DepartmentsHolder();
        departmentsHolder.setDepartments(departmentList);
        answer = CONVERTER_OBJECT_TO_STRING.getMessage(departmentsHolder);
        return answer;
    }

    String getAll() {
        String answer = null;
         List<Department> departmentList = departmentService.getAllWithoutWorkers();
        DepartmentsHolder departmentsHolder = new DepartmentsHolder();
        departmentsHolder.setDepartments(departmentList);
        answer = CONVERTER_OBJECT_TO_STRING.getMessage(departmentsHolder);
        return answer;
    }


    String create(String jsonText) {
        String answer = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Department department = objectMapper.readValue(jsonText, Department.class);
            List<Department> departmentList = departmentService.create(department);
            if (departmentList == null) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when saving. Try later.");
                answer = CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
            } else {
                DepartmentsHolder departmentsHolder = new DepartmentsHolder();
                departmentsHolder.setDepartments(departmentList);
                answer = CONVERTER_OBJECT_TO_STRING.getMessage(departmentsHolder);

            }
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.error("some json parsing exception: ", e);
            ExceptionForView exceptionForView = new ExceptionForView();
            exceptionForView.setExceptionMessage("Error in app or you send wrong format. Sorry! Try later.");
            answer = CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);

        }

        return answer;
    }


    String update(String jsonText) {
        String answer = null;
        ObjectMapper objectMapper = new ObjectMapper();


        try {
            Department department = objectMapper.readValue(jsonText, Department.class);
            List<Department> departmentList = departmentService.update(department);

            if (departmentList == null) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when saving. Try later.");
                answer = CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);

            } else {
                DepartmentsHolder departmentsHolder = new DepartmentsHolder();
                departmentsHolder.setDepartments(departmentList);
                answer = CONVERTER_OBJECT_TO_STRING.getMessage(departmentsHolder);
            }
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.error("some exception when parsing json: ", e);
            ExceptionForView exceptionForView = new ExceptionForView();
            exceptionForView.setExceptionMessage("Error in app or you send wrong format. Sorry! Try later.");
            answer = CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        }


        return answer;
    }


    String getByID(Long id) {
        String answer = null;
        Department department = departmentService.getByID(id);
        answer = CONVERTER_OBJECT_TO_STRING.getMessage(department);
        return answer;
    }


    String deleteByID(Long id) {
        String answer = null;
        Department department = departmentService.getByID(id);
        if (!departmentService.delete(department)) {
            ExceptionForView exceptionForView = new ExceptionForView();
            exceptionForView.setExceptionMessage("Error when delete. Try later.");
            department = null;
            answer = CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        } else {
            answer = CONVERTER_OBJECT_TO_STRING.getMessage(department);
        }
        return answer;
    }

    String getAnswerAboutException(String exceptionMessage) {
        String answer = null;
        ExceptionForView exceptionForView = new ExceptionForView();
        exceptionForView.setExceptionMessage(exceptionMessage);
        answer = CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        return answer;
    }

}
