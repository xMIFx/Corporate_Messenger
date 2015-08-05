package com.gitHub.xMIFx.projectConfig.springConfigs;

import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.DepartmentHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.WorkerHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.servlets.controllers.RecipientOfResponseForWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by bukatinvv on 05.08.2015.
 */
@Configuration
@Import({ServiceConfig.class, DataBaseConfig.class})
@ComponentScan(value={"com.gitHub.xMIFx.services.implementationServices"
        ,"com.gitHub.xMIFx.repositories.implementationForDAO"
        ,"com.gitHub.xMIFx.view.servlets"})
public class MainSpringConfig {

}
