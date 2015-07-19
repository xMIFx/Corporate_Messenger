package com.gitHub.xMIFx;

/**
 * Created by Vlad on 23.06.2015.
 */

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class.getName());
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();
    private static WorkerDAO workerDAO = abstractFactoryForDAOf.getWorkersDAOImpl();
    private static DepartmentDAO departmentDAO = abstractFactoryForDAOf.getDepartmentDAOImpl();

    private static String info = "some keyWords: \n" +
            "exit - for end writing.\n" +
            "dep - for creation or change department. Example: dep DepName.\n" +
            "work - for creation worker in selected department. Example: work WorkerName.\n" +
            "del_dep - for deleting department by name. Example: del_dep DepName.\n" +
            "del_work - for deleting worker by name(from department too). Example: del_work WorkerName.\n" +
            "del_work_dep - for deleting worker by name only from department. Example: del_work_dep WorkerName.\n" +
            "name can't be empty!";


    public static void main(String[] args) {
        logger.info(info);
        Department nullDepartment = departmentDAO.getByName("with out department");
        if (nullDepartment == null) {
            nullDepartment = new Department("with out department");
            departmentDAO.save(nullDepartment);
        }
        Department currentDepartment = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")))) {
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    if (line.equals("exit")) {
                        break;
                    } else if (line.startsWith("dep")) {
                        String departmentName = line.substring(4, line.length());
                        if (isNameOk(departmentName)) {
                            currentDepartment = getDepOrCreate(departmentName);
                        }
                    } else if (line.startsWith("work")) {
                        String workerName = line.substring(5, line.length());
                        if (isNameOk(workerName)) {
                            Worker newWorker = getWorkOrCreate(currentDepartment, nullDepartment, workerName);
                        }
                    } else if (line.startsWith("del_dep")) {
                        String departmentName = line.substring(8, line.length());
                        if (isNameOk(departmentName)) {
                            deleteDepartmentAndRelocateWorkersToNullDep(departmentName, nullDepartment);
                        }
                    } else if (line.startsWith("del_work_dep")) {
                        String workerName = line.substring(13, line.length());
                        if (isNameOk(workerName)) {
                            deleteWorkerFromHisDepartmentAndRelocateHimToNullDep(workerName, nullDepartment);
                        }
                    } else if (line.startsWith("del_work")) {
                        String workerName = line.substring(9, line.length());
                        if (isNameOk(workerName)) {
                            deleteWorkerFromAnywhere(workerName);
                        }

                    } else {
                        repeatInfoMessage();
                    }

                }
            }
        } catch (IOException e) {
            logger.error("readLine exception", e);
        }

        logger.info(workerDAO.getAll().toString());
        logger.info(departmentDAO.getAll().toString());

    }

    private static boolean isNameOk(String str) {
        boolean isOk = true;
        if (str == null || str.equals("")) {
            isOk = false;
            repeatInfoMessage();
        }
        return isOk;
    }

    private static void deleteWorkerFromAnywhere(String workerName) {
        Worker workerForDel = workerDAO.getByName(workerName);
        Department depForUpdate = departmentDAO.getByWorker(workerForDel);
        if (depForUpdate != null) {
            depForUpdate.removeWorker(workerForDel);
            departmentDAO.update(depForUpdate);
        }
        workerDAO.remove(workerForDel);
    }

    private static void deleteWorkerFromHisDepartmentAndRelocateHimToNullDep(String workerName, Department nullDepartment) {
        Worker workerForDel = workerDAO.getByName(workerName);
        Department depForUpdate = departmentDAO.getByWorker(workerForDel);
        if (depForUpdate != null) {
            depForUpdate.removeWorker(workerForDel);
            nullDepartment.addWorker(workerForDel);
            departmentDAO.update(depForUpdate);
            departmentDAO.update(nullDepartment);
        }

    }

    private static void deleteDepartmentAndRelocateWorkersToNullDep(String departmentName, Department nullDepartment) {
        Department depForDel = departmentDAO.getByName(departmentName);
        if (depForDel != null) {
            nullDepartment.getWorkers().addAll(depForDel.getWorkers());
            departmentDAO.remove(depForDel);
            departmentDAO.update(nullDepartment);
        }

    }

    private static Worker getWorkOrCreate(Department currentDepartment, Department nullDepartment, String workerName) {
        Worker w = workerDAO.getByName(workerName);
        if (w == null) {
            w = new Worker(workerName);
            workerDAO.save(w);
        } else {
            Department oldWorkerDep = departmentDAO.getByWorker(w);
            if (oldWorkerDep != null) {
                oldWorkerDep.removeWorker(w);
                departmentDAO.update(oldWorkerDep);
            }
        }

        if (currentDepartment != null) {
            currentDepartment.addWorker(w);
            departmentDAO.update(currentDepartment);
        } else {
            nullDepartment.addWorker(w);
            departmentDAO.update(nullDepartment);
        }

        return w;
    }

    public static Department getDepOrCreate(String departmentName) {
        Department dp = departmentDAO.getByName(departmentName);
        if (dp == null) {
            dp = new Department(departmentName);
            departmentDAO.save(dp);
        }

        return dp;
    }

    private static void repeatInfoMessage() {
        logger.info(info);
    }
}
