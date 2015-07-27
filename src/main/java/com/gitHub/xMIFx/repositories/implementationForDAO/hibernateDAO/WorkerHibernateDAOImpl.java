package com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 27.07.2015.
 */
public class WorkerHibernateDAOImpl implements WorkerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerHibernateDAOImpl.class.getName());
    private static SessionFactory sessionFact = HibernateUtil.getSessionFactory();

    @Override
    public Long save(Worker worker) {
        return null;
    }

    @Override
    public Worker getById(Long id) {

        String getByIDSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ", w.admin\n" +
                ",IFNULL(dep.name, \"Without department\") depName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where w.id  = ?";
        Session session = sessionFact.openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createSQLQuery(getByIDSql);
        query.setLong(1, id);
        Worker worker = (Worker) query.uniqueResult();
        tx.commit();
        session.close();
        return worker;
    }

    @Override
    public Worker getByName(String name) {
        return null;
    }

    @Override
    public Worker getByLoginPassword(String login, String pass) {
        return null;
    }

    @Override
    public List<Worker> getAll() {
        String getAllSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ", w.admin\n" +
                ",IFNULL(dep.name, \"Without department\") departmentName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker";
        List<Worker> workerList = null;
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {

            tx = session.beginTransaction();
            Query query = session.createSQLQuery(getAllSql)
                    .addScalar("id", new LongType())
                    .addScalar("name", new StringType())
                    .addScalar("login", new StringType())
                    .addScalar("password", new StringType())
                    .addScalar("objectVersion", new IntegerType())
                    .addScalar("admin", new BooleanType())
                    .addScalar("departmentName", new StringType());

            query.setResultTransformer(Transformers.aliasToBean(Worker.class));
            workerList = (List<Worker>) query.list();
            tx.commit();

        } catch (Throwable e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Some SQL exception", e);
        }
        if (workerList == null) {
            workerList = new ArrayList<>();
        }


        return workerList;
    }

    @Override
    public List<Worker> getByDepartment(Department department) {
        return null;
    }

    @Override
    public boolean remove(Worker worker) {
        return false;
    }

    @Override
    public boolean update(Worker worker) {
        return false;
    }

    @Override
    public List<Worker> findByName(String name) {
        return null;
    }

    @Override
    public List<Worker> findByLogin(String login) {
        return null;
    }

    @Override
    public List<Worker> findByDepartmentName(String depName) {
        return null;
    }
}
