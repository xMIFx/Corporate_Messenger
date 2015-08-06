package com.gitHub.xMIFx.projectConfig.springConfigs;

import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.HibernateUtil;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

@Configuration
public class DataBaseConfig {
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();

    @Bean(name = "workerDAO")
    public WorkerDAO workerDAO() {
        return abstractFactoryForDAOf.getWorkersDAOImpl();
    }

    @Bean(name = "departmentDAO")
    public DepartmentDAO departmentDAO() {
        return abstractFactoryForDAOf.getDepartmentDAOImpl();
    }

    @Bean(name = "sessionFact")
    public SessionFactory sessionFact() {
        return HibernateUtil.getSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFact) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFact);
        return transactionManager;
    }
}
