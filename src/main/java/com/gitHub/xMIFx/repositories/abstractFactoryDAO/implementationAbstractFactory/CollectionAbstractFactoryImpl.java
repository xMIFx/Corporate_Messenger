package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationForDAO.collectionDAO.DepartmentCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.collectionDAO.WorkerCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.ChatDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;

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

    @Override
    public ChatDAO getChatDAOImpl() {
        return null;
    }
}
