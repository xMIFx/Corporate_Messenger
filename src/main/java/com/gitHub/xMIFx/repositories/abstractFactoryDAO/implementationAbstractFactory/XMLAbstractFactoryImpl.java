package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationForDAO.xmlDAO.DepartmentXmlDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.xmlDAO.WorkerXmlDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;

/**
 * Created by Vlad on 29.06.2015.
 */
public class XMLAbstractFactoryImpl implements AbstractFactoryForDAO {
    @Override
    public WorkerDAO getWorkersDAOImpl() {
        return new WorkerXmlDAOImpl();
    }

    @Override
    public DepartmentDAO getDepartmentDAOImpl() {
        return new DepartmentXmlDAOImpl();
    }
}
