package com.gitHub.xMIFx.repositories.implementationForDAO.jsonDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.projectConfig.PropertiesForWork;
import com.gitHub.xMIFx.repositories.DTO.DepartmentsHolder;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 29.06.2015.
 */
public class DepartmentJsonDAOImpl implements DepartmentDAO {
    private static String filePath = PropertiesForWork.getPropertiesForWork().getPathToDepartments();
    private static final Logger logger = LoggerFactory.getLogger(DepartmentJsonDAOImpl.class.getName());
    // we need to clone object when someone getting it

    private static Map<Long, Department> departmentMap = new HashMap<Long, Department>();
    private static Long index = 0L;

    static {
        try {
            readAllObject();
        } catch (IOException e) {
            logger.error("Can't read file: ", e);
        }
    }

    public DepartmentJsonDAOImpl() {
    }

    private static synchronized Long increaseIndex() {
        return ++index;
    }

    @Override
    public Long save(Department department) {

        department.setId(increaseIndex());
        departmentMap.put(index, department);
        try {
            saveAllObject();
        } catch (IOException e) {
            departmentMap.remove(index);
            logger.error("exception when saving: ", e);
            department.setId(null);
            return null;
        }
        //some code for serialize all Department, even we save one
        return index;
    }

    @Override
    public Department getById(Long id) {
        return departmentMap.get(id);
    }

    @Override
    public Department getByName(String name) {
        Department departmentForFind = null;
        for (Map.Entry<Long, Department> pair : departmentMap.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                departmentForFind = pair.getValue();
                break;
            }
        }
        return departmentForFind;
    }

    @Override
    public Department getByWorker(Worker worker) {
        Department departmentForFind = null;
        for (Map.Entry<Long, Department> pair : departmentMap.entrySet()) {

            if (pair.getValue().getWorkers().contains(worker)) {
                departmentForFind = pair.getValue();
                break;
            }
        }

        return departmentForFind;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departmentList = new ArrayList<Department>();
        departmentList.addAll(departmentMap.values());
        return departmentList;
    }

    @Override
    public List<Department> getAllWithoutWorkers() {
        return null;
    }

    @Override
    public boolean remove(Department department) {
        boolean allIsOK = true;
        if (department.getId() != null) {
            Department oldValue = departmentMap.get(department.getId());
            departmentMap.remove(department.getId());
            try {
                saveAllObject();
            } catch (IOException e) {
                departmentMap.put(oldValue.getId(), oldValue);
                allIsOK = false;
                logger.error("exception when remove: ", e);
            }
        }
        //some code for serialize all Department, even we remove one
        return allIsOK;
    }

    @Override
    public boolean update(Department department) {
        //some code for serialize all Department, even we update one
        boolean allIsOK = true;

        if (department.getId() != null) {
            Department oldValue = departmentMap.get(department.getId());
            departmentMap.put(department.getId(), department);
            try {
                saveAllObject();
            } catch (IOException e) {
                departmentMap.put(oldValue.getId(), oldValue);
                allIsOK = false;
                logger.error("exception when update: ", e);
            }
        } else {
            allIsOK = false;
        }
        return allIsOK;
    }

    @Override
    public List<Long> getForUpdateByWorkers(List<Worker> workerList) {
        return null;
    }

    @Override
    public List<Department> getForUpdateByID(List<Long> listID) {
        return null;
    }

    @Override
    public List<Department> findByName(String name) {
        return null;
    }

    private static void readAllObject() throws IOException {

        File file = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        DepartmentsHolder departmentsHolder = mapper.readValue(file, DepartmentsHolder.class);
        List<Department> departmentList = departmentsHolder.getDepartments();
        for (Department dep : departmentList) {
            departmentMap.put(dep.getId(), dep);
            if (index < dep.getId()) {
                index = dep.getId();
            }
        }
        System.out.println(departmentMap.values().toString());

    }

    private boolean saveAllObject() throws IOException {
        File file = new File(filePath);

        List<Department> departmentList = new ArrayList<>();
        departmentList.addAll(departmentMap.values());
        DepartmentsHolder departmentsHolder = new DepartmentsHolder();
        departmentsHolder.setDepartments(departmentList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, departmentsHolder);

        return true;
    }


}
