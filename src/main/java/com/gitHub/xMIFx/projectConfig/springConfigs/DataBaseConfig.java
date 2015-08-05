package com.gitHub.xMIFx.projectConfig.springConfigs;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.DepartmentHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.HibernateUtil;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.WorkerHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vlad on 04.08.2015.
 */
@Configuration
public class DataBaseConfig {
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();

    @Bean(name = "workerDAO")
    public WorkerDAO workerDAO(){
        return abstractFactoryForDAOf.getWorkersDAOImpl();
    }

    @Bean(name = "departmentDAO")
    public DepartmentDAO departmentDAO(){
        return abstractFactoryForDAOf.getDepartmentDAOImpl();
    }
}
