package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationForDAO.jsonDAO.DepartmentJsonDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.jsonDAO.WorkerJsonDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;

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
