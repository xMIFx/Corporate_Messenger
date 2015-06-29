package com.gitHub.xMIFx.repositories.abstractFactoryDAO;

import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

/**
 * Created by Vlad on 29.06.2015.
 */
public interface AbstractFactoryForDAO {
    WorkerDAO getWorkersDAOImpl();
    DepartmentDAO getDepartmentDAOImpl();
}
