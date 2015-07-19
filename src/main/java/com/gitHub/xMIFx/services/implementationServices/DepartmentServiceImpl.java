package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.dto.DepartmentsHolder;
import com.gitHub.xMIFx.repositories.dto.WorkersHolder;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.interfaces.DepartmentService;
import com.gitHub.xMIFx.services.interfaces.MainService;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 11.07.2015.
 */
public class DepartmentServiceImpl extends MainServiceImpl implements DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class.getName());
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();
    private static DepartmentDAO departmentDAO = abstractFactoryForDAOf.getDepartmentDAOImpl();

    @Override
    public String find(FinderType finderType, String searchValue) {
        String answer = null;
        if (searchValue == null || "".equals(searchValue.trim())) {
            answer = getAll();
        }
        List<Department> departmentList = null;
        switch (finderType) {
            case NAME:
                departmentList = departmentDAO.findByName(searchValue);
                break;
            default:
                departmentList = new ArrayList<>();
                break;
        }
        DepartmentsHolder departmentsHolder = new DepartmentsHolder();
        departmentsHolder.setDepartments(departmentList);
        try {
            answer = getXMLMessage(departmentsHolder);
        } catch (JAXBException e) {
            logger.error("some jaxB exception: ", e);
        }
        return answer;
    }

    @Override
    public String getAll() {
        String answer = null;
        List<Department> departmentList = departmentDAO.getAllWithoutWorkers();
        DepartmentsHolder departmentsHolder = new DepartmentsHolder();
        departmentsHolder.setDepartments(departmentList);
        try {
            answer = getXMLMessage(departmentsHolder);
        } catch (JAXBException e) {
            logger.error("some jaxB exception: ", e);
        }
        return answer;
    }

    @Override
    public String create(String jsonText) {
        String answer = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Department department = objectMapper.readValue(jsonText, Department.class);
            List<Long> departmentsID = departmentDAO.getForUpdateByWorkers(department.getWorkers());
            if (departmentDAO.save(department) == null) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when saving. Try later.");
                answer = getXMLMessage(exceptionForView);
            } else {
                if (!departmentsID.contains(department.getId())) {
                    departmentsID.add(department.getId());
                } else {/*NOP*/}
                List<Department> departmentList = departmentDAO.getForUpdateByID(departmentsID);
                DepartmentsHolder departmentsHolder = new DepartmentsHolder();
                departmentsHolder.setDepartments(departmentList);
                answer = getXMLMessage(departmentsHolder);

            }
        } catch (JAXBException e) {
            logger.error("some jaxB exception: ", e);
        } catch (IOException e) {
            logger.error("some json parsing exception: ", e);
        }
        return answer;
    }

    @Override
    public String update(String jsonText) {
        String answer = null;
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(jsonText);
        try {
            Department department = objectMapper.readValue(jsonText, Department.class);

            List<Long> departmentsID = departmentDAO.getForUpdateByWorkers(department.getWorkers());
            if (!departmentsID.contains(department.getId())) {
                departmentsID.add(department.getId());
            } else {/*NOP*/}
            if (!departmentDAO.update(department)) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when saving. Try later.");
                answer = getXMLMessage(exceptionForView);

            } else {
                List<Department> departmentList = departmentDAO.getForUpdateByID(departmentsID);
                DepartmentsHolder departmentsHolder = new DepartmentsHolder();
                departmentsHolder.setDepartments(departmentList);
                answer = getXMLMessage(departmentsHolder);
            }

        } catch (JAXBException e) {
            logger.error("some jaxB exception: ", e);
        } catch (IOException e) {
            logger.error("some exception when parsing json: ", e);
        }
        return answer;
    }

    @Override
    public String getByID(Long id) {
        String answer = null;
        Department department = departmentDAO.getById(id);
        try {
            answer = getXMLMessage(department);
        } catch (JAXBException e) {
            logger.error("some jaxB exception: ", e);
        }
        return answer;
    }

    @Override
    public String deleteByID(Long id) {
        String answer = null;
        Department department = departmentDAO.getById(id);
        try {
            if (!departmentDAO.remove(department)) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when delete. Try later.");
                department = null;
                answer = getXMLMessage(exceptionForView);
            } else {
                answer = getXMLMessage(department);
            }
        } catch (JAXBException e) {
            logger.error("some jaxB exception: ", e);
        }
        return answer;
    }
}
