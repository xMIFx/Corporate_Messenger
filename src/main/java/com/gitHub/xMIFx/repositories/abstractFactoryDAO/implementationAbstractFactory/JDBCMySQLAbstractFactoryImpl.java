package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationForDAO.jdbcMySQLDAO.DepartmentJdbcMySQLDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.jdbcMySQLDAO.WorkerJdbcMySQLDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.ChatDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;

public class JDBCMySQLAbstractFactoryImpl implements AbstractFactoryForDAO {
    @Override
    public WorkerDAO getWorkersDAOImpl() {
        return new WorkerJdbcMySQLDAOImpl();
    }

    @Override
    public DepartmentDAO getDepartmentDAOImpl() {
        return new DepartmentJdbcMySQLDAOImpl();
    }

    @Override
    public ChatDAO getChatDAOImpl() {
        return null;
    }
}
