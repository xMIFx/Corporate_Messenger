package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.interfaces.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 11.07.2015.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerServiceImpl.class.getName());
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();
    private static DepartmentDAO departmentDAO = abstractFactoryForDAOf.getDepartmentDAOImpl();
   /*@Resource
    private DepartmentDAO departmentDAO;

    public DepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }*/

    @Override
    public List<Department> find(FinderType finderType, String searchValue) {
        List<Department> departmentList = null;
        if (searchValue == null || "".equals(searchValue.trim())) {
            departmentList = getAll();
        }

        switch (finderType) {
            case NAME:
                departmentList = departmentDAO.findByName(searchValue);
                break;
            default:
                departmentList = new ArrayList<>();
                break;
        }

        return departmentList;    }

    @Override
    public List<Department> getAllWithoutWorkers() {

        List<Department> departmentList = departmentDAO.getAllWithoutWorkers();
        return departmentList;
    }

    @Override
    public List<Department> getAll() {

        List<Department> departmentList = departmentDAO.getAll();
        return departmentList;
    }

    @Override
    public List<Department> create(Department department) {
        List<Department> listForReturn = null;
        List<Long> departmentsID = departmentDAO.getByWorkers(department.getWorkers());
        if (departmentDAO.save(department) == null) {

        } else {
            if (!departmentsID.contains(department.getId())) {
                departmentsID.add(department.getId());
            } else {/*NOP*/}
            listForReturn = departmentDAO.getByListIDs(departmentsID);
        }

        return listForReturn;
    }

    @Override
    public List update(Department department) {
        List<Department> listForReturn = null;
        List<Long> departmentsID = departmentDAO.getByWorkers(department.getWorkers());
        if (!departmentsID.contains(department.getId())) {
            departmentsID.add(department.getId());
        } else {/*NOP*/}
        if (!departmentDAO.update(department)) {
        } else {
            listForReturn = departmentDAO.getByListIDs(departmentsID);
        }
        return listForReturn;
    }

    @Override
    public Department getByID(Long id) {

        Department department = departmentDAO.getById(id);

        return department;
    }

    @Override
    public boolean delete(Department department) {
        return departmentDAO.remove(department);
    }


}
