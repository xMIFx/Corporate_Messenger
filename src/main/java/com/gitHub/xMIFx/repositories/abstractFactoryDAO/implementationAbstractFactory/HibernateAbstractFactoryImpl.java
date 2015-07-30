package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.DepartmentHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.WorkerHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;

/**
 * Created by Vlad on 27.07.2015.
 */
public class HibernateAbstractFactoryImpl  implements AbstractFactoryForDAO{
    @Override
    public WorkerDAO getWorkersDAOImpl() {
        return new WorkerHibernateDAOImpl();
    }

    @Override
    public DepartmentDAO getDepartmentDAOImpl() {
        return new DepartmentHibernateDAOImpl();
    }
}