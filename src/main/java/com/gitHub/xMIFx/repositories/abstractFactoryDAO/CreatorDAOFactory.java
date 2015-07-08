package com.gitHub.xMIFx.repositories.abstractFactoryDAO;

import com.gitHub.xMIFx.projectConfig.PropertiesForWork;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.implementationAbstractFactory.*;

/**
 * Created by Vlad on 29.06.2015.
 */
public abstract class CreatorDAOFactory {
    private static PropertiesForWork propertiesForWork = PropertiesForWork.getPropertiesForWork();

    public static AbstractFactoryForDAO getAbstractFactoryForDAO() {
        AbstractFactoryForDAO factoryForDAO;
        switch (propertiesForWork.getCurrentTypeOfDAO()) {
            case COLLECTIONS:
                factoryForDAO = new CollectionAbstractFactoryImpl();
                break;
            case SERIALIZING:
                factoryForDAO = new SerializingAbstractFactoryImpl();
                break;
            case XML:
                factoryForDAO = new XMLAbstractFactoryImpl();
                break;
            case JSON:
                factoryForDAO = new JSONAbstractFactoryImpl();
                break;
            case JDBCMYSQL:
                factoryForDAO = new JDBCMySQLAbstractFactoryImpl();
                break;
            default:
                factoryForDAO = new CollectionAbstractFactoryImpl();
                break;
        }
        return factoryForDAO;
    }

}
