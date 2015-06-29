package com.gitHub.xMIFx.repositories.implementationDAO.serializableDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.projectConfig.PropertiesForWork;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 28.06.2015.
 */
public class DepartmentSerialDAOImpl implements DepartmentDAO {
    private static String filePath = PropertiesForWork.getPropertiesForWork().getPathToDepartments();
    private static final Logger logger = LoggerFactory.getLogger(WorkerSerialDAOImpl.class.getName());
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

    public DepartmentSerialDAOImpl() {
    }

    @Override
    public Long save(Department department) {
        index++;
        department.setId(index);
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

    private static void readAllObject() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                Department readDepartment = new Department();
                try {
                    readDepartment.readExternal(in);
                } catch (ClassNotFoundException e) {
                    logger.error("Can't read some dep from file: ", e);
                }
                if (readDepartment.getId() != null) {
                    departmentMap.put(readDepartment.getId(), readDepartment);
                    if (index < readDepartment.getId()) {
                        index = readDepartment.getId();
                    } else {
                        /*NOP*/
                    }
                } else {
                    throw new IOException("some problem with id");
                }
            }

        }
        System.out.println(departmentMap.values().toString());

    }

    private boolean saveAllObject() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
            out.writeInt(departmentMap.size());
            for (Map.Entry<Long, Department> pair : departmentMap.entrySet()) {
                pair.getValue().writeExternal(out);
            }
        }
        return true;
    }

}
