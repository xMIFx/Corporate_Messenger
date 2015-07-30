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
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            tx = session.beginTransaction();
            try {


                Query query = session.createSQLQuery(getByIDSql).addScalar("id", new LongType())
                        .addScalar("name", new StringType())
                        .addScalar("login", new StringType())
                        .addScalar("password", new StringType())
                        .addScalar("objectVersion", new IntegerType())
                        .addScalar("admin", new BooleanType())
                        .addScalar("departmentName", new StringType());
                ;
                query.setLong("idParameter", id);
                worker = (Worker) query.setResultTransformer(Transformers.aliasToBean(Worker.class)).uniqueResult();
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
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
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            tx = session.beginTransaction();
            try {
                Query query = session.createSQLQuery(getByNameSql).addScalar("id", new LongType())
                        .addScalar("name", new StringType())
                        .addScalar("login", new StringType())
                        .addScalar("password", new StringType())
                        .addScalar("objectVersion", new IntegerType())
                        .addScalar("admin", new BooleanType())
                        .addScalar("departmentName", new StringType());
                query.setString("nameParameter", name);
                worker = (Worker) query.setResultTransformer(Transformers.aliasToBean(Worker.class)).uniqueResult();
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
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
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            tx = session.beginTransaction();
            try {
                Query query = session.createSQLQuery(getByLoginPasSql).addScalar("id", new LongType())
                        .addScalar("name", new StringType())
                        .addScalar("login", new StringType())
                        .addScalar("password", new StringType())
                        .addScalar("objectVersion", new IntegerType())
                        .addScalar("admin", new BooleanType())
                        .addScalar("departmentName", new StringType());
                query.setString("loginParameter", login);
                query.setString("passParameter", pass);
                worker = (Worker) query.setResultTransformer(Transformers.aliasToBean(Worker.class)).uniqueResult();
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
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
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
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
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(getByDepSql)
                        .addScalar("id", new LongType())
                        .addScalar("name", new StringType())
                        .addScalar("login", new StringType())
                        .addScalar("password", new StringType())
                        .addScalar("objectVersion", new IntegerType())
                        .addScalar("admin", new BooleanType())
                        .addScalar("departmentName", new StringType());
                query.setLong("idDepartmentParameter", department.getId());
                query.setResultTransformer(Transformers.aliasToBean(Worker.class));
                workerList = (List<Worker>) query.list();
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
        }
        if (workerList == null) {
            workerList = new ArrayList<>();
        }
        return workerList;
    }

    @Override
    public boolean remove(Worker worker) {
        boolean itsOk = true;
        if (worker.getId() == null) {
            itsOk = false;
        }
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                session.delete(worker);
                tx.commit();
                itsOk = true;
            } catch (Throwable e) {
                itsOk = false;
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
        }
        return itsOk;
    }

    @Override
    public boolean update(Worker worker) {
        boolean itsOk = true;
        if (worker.getId() == null) {
            itsOk = false;
        }
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                session.update(worker);
               // session.replicate(worker, ReplicationMode.LATEST_VERSION);
                tx.commit();
                itsOk = true;
            } catch (Throwable e) {
                itsOk = false;
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
        }
        return itsOk;
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
        Transaction tx = null;
        String searchValue = "%" + name + "%";
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(findByNameSql)
                        .addScalar("id", new LongType())
                        .addScalar("name", new StringType())
                        .addScalar("login", new StringType())
                        .addScalar("password", new StringType())
                        .addScalar("objectVersion", new IntegerType())
                        .addScalar("admin", new BooleanType())
                        .addScalar("departmentName", new StringType());
                query.setString("nameParameter", searchValue);
                query.setResultTransformer(Transformers.aliasToBean(Worker.class));
                workerList = (List<Worker>) query.list();
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
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
        Transaction tx = null;
        String searchValue = "%" + login + "%";
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(findByLoginSql)
                        .addScalar("id", new LongType())
                        .addScalar("name", new StringType())
                        .addScalar("login", new StringType())
                        .addScalar("password", new StringType())
                        .addScalar("objectVersion", new IntegerType())
                        .addScalar("admin", new BooleanType())
                        .addScalar("departmentName", new StringType());
                query.setString("loginParameter", searchValue);
                query.setResultTransformer(Transformers.aliasToBean(Worker.class));
                workerList = (List<Worker>) query.list();
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
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
        Transaction tx = null;
        String searchValue = "%" + depName + "%";
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(findByDepNameSql)
                        .addScalar("id", new LongType())
                        .addScalar("name", new StringType())
                        .addScalar("login", new StringType())
                        .addScalar("password", new StringType())
                        .addScalar("objectVersion", new IntegerType())
                        .addScalar("admin", new BooleanType())
                        .addScalar("departmentName", new StringType());
                query.setString("depNameParameter", searchValue);
                query.setResultTransformer(Transformers.aliasToBean(Worker.class));
                workerList = (List<Worker>) query.list();
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
        }
        if (workerList == null) {
            workerList = new ArrayList<>();
        }
        return workerList;
    }
}
