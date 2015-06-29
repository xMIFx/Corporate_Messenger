package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO.DepartmentCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO.WorkerCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

/**
 * Created by Vlad on 29.06.2015.
 */
public class CollectionAbstractFactoryImpl implements AbstractFactoryForDAO {
    @Override
    public WorkerDAO getWorkersDAOImpl() {
        return new WorkerCollectionDAOImpl();
    }

    @Override
    public DepartmentDAO getDepartmentDAOImpl() {
        return new DepartmentCollectionDAOImpl();
    }
}
