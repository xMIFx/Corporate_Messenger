package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationDAO.jsonDAO.DepartmentJsonDAOImpl;
import com.gitHub.xMIFx.repositories.implementationDAO.jsonDAO.WorkerJsonDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

/**
 * Created by Vlad on 29.06.2015.
 */
public class JSONAbstractFactoryImpl implements AbstractFactoryForDAO {
    @Override
    public WorkerDAO getWorkersDAOImpl() {
        return new WorkerJsonDAOImpl();
    }

    @Override
    public DepartmentDAO getDepartmentDAOImpl() {
        return new DepartmentJsonDAOImpl();
    }
}
