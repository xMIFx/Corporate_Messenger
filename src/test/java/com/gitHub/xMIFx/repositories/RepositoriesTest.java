package com.gitHub.xMIFx.repositories;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO.DepartmentCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.implementationDAO.collectionDAO.WorkerCollectionDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
/**
 * Created by Vlad on 02.07.2015.
 */
public class RepositoriesTest {
    @Test
    public void testingWorkerDAO() {
        WorkerDAO workerDAO = new WorkerCollectionDAOImpl();
        Worker worker = new Worker("Vlad");
        workerDAO.save(worker);

        Worker readWorker = workerDAO.getByName("Vlad");
        assertThat("not the same object after save-getByName",readWorker, equalTo(worker));

        readWorker = workerDAO.getById(worker.getId());
        assertThat("not the same object after save-getById", readWorker, equalTo(worker));

        worker.setLogin("MIF");
        workerDAO.update(worker);

        readWorker = workerDAO.getByName("Vlad");
        assertThat("not the same object after update-getByName", readWorker, equalTo(worker));


        readWorker = workerDAO.getById(worker.getId());
        assertThat("not the same object after update-getByID", readWorker, equalTo(worker));

        workerDAO.remove(worker);

        readWorker = workerDAO.getByName("Vlad");
        assertThat("remove don't work", readWorker, is(nullValue()));
    }

    @Test
    public void testingDepartmentDAO() {
        DepartmentDAO departmentDAO = new DepartmentCollectionDAOImpl();
        Department department = new Department("Vlad");
        departmentDAO.save(department);

        Department readDepartment = departmentDAO.getByName("Vlad");
        assertThat("not the same object after save-getByName", readDepartment, equalTo(department));

        readDepartment = departmentDAO.getById(department.getId());
        assertThat("not the same object after save-getByID", readDepartment, equalTo(department));

        department.addWorker(new Worker());
        departmentDAO.update(department);

        readDepartment = departmentDAO.getByName("Vlad");
        assertThat("not the same object after update-getByName", readDepartment, equalTo(department));

        readDepartment = departmentDAO.getById(department.getId());
        assertThat("not the same object after update-getByID", readDepartment, equalTo(department));

        departmentDAO.remove(department);

        readDepartment = departmentDAO.getByName("Vlad");
        assertThat("remove don't work", readDepartment, is(nullValue()));

    }
}
