package com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class DepartmentHibernateDAOImpl implements DepartmentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentHibernateDAOImpl.class.getName());

    @Autowired
    @Qualifier("sessionFact")
    private SessionFactory sessionFact;

    @Override
    public Long save(Department department) {
        if (department.getId() != null) {
            return department.getId();
        }
        String sqlDeleteBindingWorker = "DELETE FROM corporate_messenger.departmentworkers WHERE idworker IN (:workersIDParameter);";
        List<Long> workersID = department.getWorkers().stream().map(Worker::getId).collect(Collectors.toList());
        Transaction tx = null;
        Long idForReturn = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                Query query = session.createSQLQuery(sqlDeleteBindingWorker);
                query.setParameterList("workersIDParameter", workersID);
                query.executeUpdate();
                idForReturn = (Long) session.save(department);
                department.setId(idForReturn);
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
    public Department getById(Long id) {
        Department dep = null;
        try (Session session = sessionFact.openSession()) {
            dep = (Department) session.createCriteria(Department.class)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        return dep;
    }

    @Override
    public Department getByName(String name) {
        Department dep = null;

        try (Session session = sessionFact.openSession()) {
            dep = (Department) session.createCriteria(Department.class)
                    .add(Restrictions.eq("name", name))
                    .uniqueResult();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        return dep;
    }

    @Override
    public Department getByWorker(Worker worker) {
        Department department = null;

        try (Session session = sessionFact.openSession()) {
            department = (Department) session.createCriteria(Department.class)
                    .createAlias("workers", "workers", JoinType.LEFT_OUTER_JOIN)
                    .add(Restrictions.eq("id", worker.getId()))
                    .uniqueResult();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        return department;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departmentList = null;

        try (Session session = sessionFact.openSession()) {
            departmentList = (List<Department>) session.createCriteria(Department.class).list();

        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (departmentList == null) {
            departmentList = new ArrayList<>();
        }
        return departmentList;
    }


    @Override
    public List<Department> getAllWithoutWorkers() {
        List<Department> departmentList = null;

        try (Session session = sessionFact.openSession()) {
            departmentList = (List<Department>) session.createCriteria(Department.class).list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (departmentList == null) {
            departmentList = new ArrayList<>();
        }
        return departmentList;
    }

    @Override
    public boolean remove(Department department) {

        if (department.getId() == null) {
            return false;
        }
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                session.delete(department);
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
    public boolean update(Department department) {

        if (department.getId() == null) {
            return false;
        }
        String sqlDeleteBindingWorker = "DELETE FROM corporate_messenger.departmentworkers WHERE idworker IN (:workersIDParameter);";
        List<Long> workersID = department.getWorkers().stream().map(Worker::getId).collect(Collectors.toList());
        Transaction tx = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                Department depFromBase = session.load(Department.class, department.getId());
                if (depFromBase.getObjectVersion() != department.getObjectVersion()) {
                    return false;
                } else {
                    department.setObjectVersion(department.getObjectVersion() + 1);
                    Query query = session.createSQLQuery(sqlDeleteBindingWorker);
                    query.setParameterList("workersIDParameter", workersID);
                    query.executeUpdate();
                    session.merge(department);
                }
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
    public List<Long> getByWorkers(List<Worker> workerList) {
        String hqlQuery = "select dep.id from Department dep  " +
                "join dep.workers as worker " +
                "where worker.id in (:workersParameter)";

        List<Long> workersId = workerList.stream().map(Worker::getId).collect(Collectors.toList());
        List<Long> departmentsID = null;

        try (Session session = sessionFact.openSession()) {
            departmentsID = (List<Long>) session.createQuery(hqlQuery)
                    .setParameterList("workersParameter", workersId).list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (departmentsID == null) {
            departmentsID = new ArrayList<>();
        }
        return departmentsID;

    }

    @Override
    public List<Department> getByListIDs(List<Long> listID) {
        List<Department> departmentList = null;

        try (Session session = sessionFact.openSession()) {
            departmentList = session.createCriteria(Department.class)
                    .add(Restrictions.in("id", listID))
                    .list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (departmentList == null) {
            departmentList = new ArrayList<>();
        }
        return departmentList;
    }

    @Override
    public List<Department> findByName(String name) {

        String searchValue = "%" + name + "%";
        List<Department> departmentList = null;

        try (Session session = sessionFact.openSession()) {
            departmentList = session.createCriteria(Department.class)
                    .add(Restrictions.like("name", searchValue))
                    .list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (departmentList == null) {
            departmentList = new ArrayList<>();
        }
        return departmentList;
    }
}
