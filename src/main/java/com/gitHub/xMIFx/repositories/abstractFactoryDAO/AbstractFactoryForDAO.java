package com.gitHub.xMIFx.repositories.abstractFactoryDAO;

import com.gitHub.xMIFx.repositories.interfacesForDAO.ChatDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;

public interface AbstractFactoryForDAO {
    WorkerDAO getWorkersDAOImpl();
    DepartmentDAO getDepartmentDAOImpl();
    ChatDAO getChatDAOImpl();
}
