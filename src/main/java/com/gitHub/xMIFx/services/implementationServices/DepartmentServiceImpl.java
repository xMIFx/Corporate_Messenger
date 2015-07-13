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
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
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
    public String create(String jsonText) {
        return null;
    }

    @Override
    public String update(String jsonText) {
        String answer = null;
        ObjectMapper objectMapper = new ObjectMapper();

      /*  Map<String, Object> myMap = null;*/
        try {
            Department department = objectMapper.readValue(jsonText, Department.class);

           /* myMap = objectMapper.readValue(jsonText, new TypeReference<HashMap<String, Object>>() {
            });

            String name = (String) myMap.get("name");
            Long id = Long.valueOf((String)myMap.get("id"));
            int objVersion = Integer.valueOf((String)myMap.get("objectVersion"));
            int countWorkers = Integer.valueOf((String) myMap.get("workersCount"));
            ArrayNode node = objectMapper.readValue(myMap.get("workers"), ArrayNode.class);
            String workers =  myMap.get("workers");
            if (workers != null) {
                Worker[] workers1 = objectMapper.readValue(
                        myMap.get("workers"), Worker[].class);
                System.out.println(workers1.length);
                System.out.println(workers1[0]);
            }*/

            if (!departmentDAO.update(department)) {
                ExceptionForView exceptionForView = new ExceptionForView();
                exceptionForView.setExceptionMessage("Error when saving. Try later.");

                answer = getXMLMessage(exceptionForView);

            } else {
                answer = getXMLMessage(department);
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
