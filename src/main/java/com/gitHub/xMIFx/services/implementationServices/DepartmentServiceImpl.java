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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 11.07.2015.
 */
public class DepartmentServiceImpl implements DepartmentService {
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
    public String create(String name, String login, String password) {
        return null;
    }

    @Override
    public String update(Long id, String name, String login, String password, int objVersion, String depName) {
        return null;
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
        return null;
    }


    private <T> String getXMLMessage(T value) throws JAXBException {
        JAXBContext jaxbRootContext = null;
        StringWriter writer = new StringWriter();
        jaxbRootContext = JAXBContext.newInstance(value.getClass());
        Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(value, writer);
        System.out.println(writer.toString());
        return writer.toString();
    }
}
