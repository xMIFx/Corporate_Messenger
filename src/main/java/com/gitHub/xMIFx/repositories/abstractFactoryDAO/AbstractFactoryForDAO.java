package com.gitHub.xMIFx.repositories.abstractFactoryDAO;

import com.gitHub.xMIFx.repositories.interfacesForDAO.ChatDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;

/**
 * Created by Vlad on 29.06.2015.
 */
public interface AbstractFactoryForDAO {
    WorkerDAO getWorkersDAOImpl();
    DepartmentDAO getDepartmentDAOImpl();
    ChatDAO getChatDAOImpl();
}
