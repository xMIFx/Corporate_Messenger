package com.gitHub.xMIFx.projectConfig;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vlad on 04.08.2015.
 */
@Configuration
@ComponentScan(value={"com.gitHub.xMIFx.repositories.implementationForDAO"})
public class DataBaseConfig {

    @Bean(name = "sessionFact")
    public SessionFactory sessionFact() {
        org.hibernate.cfg.Configuration conf = new org.hibernate.cfg.Configuration().configure();
        return conf.buildSessionFactory();
    }
}
