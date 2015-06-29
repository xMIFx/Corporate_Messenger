package com.gitHub.xMIFx.projectConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Vlad on 29.06.2015.
 */
public class PropertiesForWork {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesForWork.class.getName());
    private static final PropertiesForWork propertiesForWork = new PropertiesForWork();
    private String pathToRepo;
    private TypeOfDAO currentTypeOfDAO;
    private String pathToWorkers;
    private String pathToDepartments;

    private PropertiesForWork() {
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(in);
            this.pathToRepo = prop.getProperty("pathToRepositories");
            String DAOType = prop.getProperty("typeOfDAO");
            switch (DAOType) {
                case "COLLECTIONS":
                    this.currentTypeOfDAO = TypeOfDAO.COLLECTIONS;
                    break;
                case "SERIALIZING":
                    this.currentTypeOfDAO = TypeOfDAO.SERIALIZING;
                    this.pathToWorkers = this.pathToRepo + "/workers.out";
                    this.pathToDepartments = this.pathToRepo + "/departments.out";
                    break;
                case "XML":
                    this.currentTypeOfDAO = TypeOfDAO.XML;
                    this.pathToWorkers = this.pathToRepo + "/workers.xml";
                    this.pathToDepartments = this.pathToRepo + "/departments.xml";
                    break;
                case "JSON":
                    this.currentTypeOfDAO = TypeOfDAO.JSON;
                    this.pathToWorkers = this.pathToRepo + "/workers.json";
                    this.pathToDepartments = this.pathToRepo + "/departments.json";
                    break;
                case "JDBC":
                    this.currentTypeOfDAO = TypeOfDAO.JDBC;
                    break;
                default:
                    setDefaultValues();
                    break;
            }
        } catch (FileNotFoundException e) {
            logger.error("Exception in load properties: no file", e);
            setDefaultValues();
        } catch (IOException e) {
            logger.error("Exception in load properties: ", e);
            setDefaultValues();
        }

    }

    private void setDefaultValues() {
        this.currentTypeOfDAO = TypeOfDAO.SERIALIZING;
        this.pathToWorkers = this.pathToRepo + "/workers.out";
        this.pathToDepartments = this.pathToRepo + "/departments.out";
    }

    public static PropertiesForWork getPropertiesForWork() {
        return propertiesForWork;
    }

    public String getPathToRepo() {
        return pathToRepo;
    }

    public TypeOfDAO getCurrentTypeOfDAO() {
        return currentTypeOfDAO;
    }

    public String getPathToWorkers() {
        return pathToWorkers;
    }

    public String getPathToDepartments() {
        return pathToDepartments;
    }
}
