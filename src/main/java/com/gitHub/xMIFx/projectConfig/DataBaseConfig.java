package com.gitHub.xMIFx.projectConfig;

import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vlad on 04.08.2015.
 */
@Configuration
public class DataBaseConfig {
    @Bean
    public SessionFactory sessionFact() {
           return HibernateUtil.getSessionFactory();
    }
}
