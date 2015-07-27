package com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Created by Vlad on 27.07.2015.
 */
public class HibernateUtil {
    private static final SessionFactory sf = configureSF();


    private static SessionFactory configureSF()
            throws HibernateException {

        Configuration conf = new Configuration().configure();
        return conf.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }
}
