package com.gitHub.xMIFx.projectConfig;

import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.DepartmentHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.WorkerHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by bukatinvv on 05.08.2015.
 */
/*@Configuration*/
public class ServiceConfig {

   /* @Bean
    public WorkerDAO workerDAO(){
        return new WorkerHibernateDAOImpl();
    }
    @Bean
   public DepartmentDAO departmentDAO(){
        return  new DepartmentHibernateDAOImpl();
    }
    @Bean
    public WorkerService workerService(){
        return new WorkerServiceImpl();
    }*/
}
