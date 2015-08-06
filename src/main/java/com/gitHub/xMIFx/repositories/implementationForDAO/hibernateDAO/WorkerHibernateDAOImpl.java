package com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;


import org.hibernate.*;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import java.util.ArrayList;
import java.util.List;

public class WorkerHibernateDAOImpl implements WorkerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerHibernateDAOImpl.class.getName());
    @Autowired
    @Qualifier("sessionFact")
    private SessionFactory sessionFact;

    @Override
    public Long save(Worker worker) {
        if (worker.getId() != null) {
            return worker.getId();
        }
        Transaction tx = null;
        Long idForReturn = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                idForReturn = (Long) session.save(worker);
                worker.setDepartmentName("Without department");
                worker.setId(idForReturn);
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
        }
        return idForReturn;
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
                ",IFNULL(dep.name, \"Without department\") departmentName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker\n" +
                "where w.id  = :idParameter";

        Worker worker = null;
        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, getByIDSql);
            query.setLong("idParameter", id);
            worker = (Worker) query.uniqueResult();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        return worker;
    }

    @Override
    public Worker getByName(String name) {
        String getByNameSql = "SELECT \n" +
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
                "on w.id = depwork.idworker\n" +
                "where w.name  = :nameParameter";

        Worker worker = null;
        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, getByNameSql);
            query.setString("nameParameter", name);
            worker = (Worker) query.uniqueResult();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        return worker;
    }

    @Override
    public Worker getByLoginPassword(String login, String pass) {
        String getByLoginPasSql = "SELECT \n" +
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
                "on w.id = depwork.idworker\n" +
                "where w.login  = :loginParameter" +
                " and w.password = :passParameter";

        Worker worker = null;

        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, getByLoginPasSql);
            query.setString("loginParameter", login);
            query.setString("passParameter", pass);
            worker = (Worker) query.uniqueResult();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }

        return worker;
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
        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, getAllSql);
            workerList = (List<Worker>) query.list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (workerList == null) {
            workerList = new ArrayList<>();
        }
        return workerList;
    }

    @Override
    public List<Worker> getByDepartment(Department department) {
        String getByDepSql = "SELECT \n" +
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
                "on w.id = depwork.idworker\n" +
                "where dep.id  = :idDepartmentParameter";

        List<Worker> workerList = null;
        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, getByDepSql);
            query.setLong("idDepartmentParameter", department.getId());
            workerList = (List<Worker>) query.list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }

        if (workerList == null) {
            workerList = new ArrayList<>();
        }
        return workerList;
    }

    @Override
    public boolean remove(Worker worker) {

        if (worker.getId() == null) {
            return false;
        }
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                session.delete(worker);
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean update(Worker worker) {

        if (worker.getId() == null) {
            return false;
        }
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                session.update(worker);
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Worker> findByName(String name) {
        String findByNameSql = "SELECT \n" +
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
                "on w.id = depwork.idworker\n" +
                "where w.name  LIKE :nameParameter";

        List<Worker> workerList = null;
        String searchValue = "%" + name + "%";
        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, findByNameSql);
            query.setString("nameParameter", searchValue);
            workerList = (List<Worker>) query.list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (workerList == null) {
            workerList = new ArrayList<>();
        }
        return workerList;
    }

    @Override
    public List<Worker> findByLogin(String login) {
        String findByLoginSql = "SELECT \n" +
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
                "on w.id = depwork.idworker\n" +
                "where w.login  LIKE :loginParameter";

        List<Worker> workerList = null;
        String searchValue = "%" + login + "%";
        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, findByLoginSql);
            query.setString("loginParameter", searchValue);
            workerList = (List<Worker>) query.list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }

        if (workerList == null) {
            workerList = new ArrayList<>();
        }
        return workerList;
    }

    @Override
    public List<Worker> findByDepartmentName(String depName) {
        String findByDepNameSql = "SELECT \n" +
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
                "on w.id = depwork.idworker\n" +
                "where  IFNULL(dep.name, \"Without department\")  LIKE :depNameParameter";
        List<Worker> workerList = null;
        String searchValue = "%" + depName + "%";
        try (Session session = sessionFact.openSession()) {
            Query query = getSQLQueryWithScalarForWorker(session, findByDepNameSql);
            query.setString("depNameParameter", searchValue);
            workerList = (List<Worker>) query.list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }

        if (workerList == null) {
            workerList = new ArrayList<>();
        }

        return workerList;
    }

    private Query getSQLQueryWithScalarForWorker(Session session, String sql) {
        Query query = session.createSQLQuery(sql)
                .addScalar("id", new LongType())
                .addScalar("name", new StringType())
                .addScalar("login", new StringType())
                .addScalar("password", new StringType())
                .addScalar("objectVersion", new IntegerType())
                .addScalar("admin", new BooleanType())
                .addScalar("departmentName", new StringType());
        query.setResultTransformer(Transformers.aliasToBean(Worker.class));
        return query;
    }
}
