package com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO.DepartmentJdbcMySQLDAOImpl;
import com.gitHub.xMIFx.repositories.implementationDAO.jdbcMySQLDAO.WorkerJdbcMySQLDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;

/**
 * Created by Vlad on 29.06.2015.
 */
public class JDBCMySQLAbstractFactoryImpl implements AbstractFactoryForDAO {
    @Override
    public WorkerDAO getWorkersDAOImpl() {
        return new WorkerJdbcMySQLDAOImpl();
    }

    @Override
    public DepartmentDAO getDepartmentDAOImpl() {
        return new DepartmentJdbcMySQLDAOImpl();
    }
}
