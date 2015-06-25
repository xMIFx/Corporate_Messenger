package com.gitHub.xMIFx;

/**
 * Created by Vlad on 23.06.2015.
 */

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.packDAO.implementationDAO.DepartmentCollectionDAOImpl;
import com.gitHub.xMIFx.packDAO.implementationDAO.WorkerCollectionDAOImpl;
import com.gitHub.xMIFx.packDAO.interfacesDAO.DepartmentDAO;
import com.gitHub.xMIFx.packDAO.interfacesDAO.WorkerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class.getName());
    private static DepartmentDAO departmentDAO = new DepartmentCollectionDAOImpl();
    private static WorkerDAO workerDAO = new WorkerCollectionDAOImpl();
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
        Department nullDepartment = departmentDAO.getDepartmentByName("with out department");
        if (nullDepartment == null) {
            nullDepartment = new Department("with out department");
            departmentDAO.saveDepartment(nullDepartment);
        }
        Department currentDepartment = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String line = reader.readLine();
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
                        deleteWorkerFromHisDepartmnetAndRelocateHimToNullDep(workerName, nullDepartment);
                    }
                } else if (line.startsWith("del_work")) {
                    String workerName = line.substring(9, line.length());
                    if (isNameOk(workerName)) {
                        deleteWorkerFromAveryWhere(workerName);
                    }

                } else {
                    repeatInfoMessage();
                }

            }
        } catch (IOException e) {
            logger.error("readLine exception", e);
        }

        logger.info(workerDAO.getAllWorkers().toString());
        logger.info(departmentDAO.getAllDepartments().toString());

    }

    private static boolean isNameOk(String str) {
        boolean isOk = true;
        if (str == null || str.equals("")) {
            isOk = false;
            repeatInfoMessage();
        }
        return isOk;
    }

    private static void deleteWorkerFromAveryWhere(String workerName) {
        Worker workerForDel = workerDAO.getWorkerByName(workerName);
        Department depForUpdate = departmentDAO.getDepartmentByWorker(workerForDel);
        if (depForUpdate != null) {
            depForUpdate.removeWorker(workerForDel);
        }
        departmentDAO.updateDepartment(depForUpdate);
        workerDAO.removeWorker(workerForDel);
    }

    private static void deleteWorkerFromHisDepartmnetAndRelocateHimToNullDep(String workerName, Department nullDepartment) {
        Worker workerForDel = workerDAO.getWorkerByName(workerName);
        Department depForUpdate = departmentDAO.getDepartmentByWorker(workerForDel);
        if (depForUpdate != null) {
            depForUpdate.removeWorker(workerForDel);
            nullDepartment.addWorker(workerForDel);
        }
        departmentDAO.updateDepartment(depForUpdate);
    }

    private static void deleteDepartmentAndRelocateWorkersToNullDep(String departmentName, Department nullDepartment) {
        Department depForDel = departmentDAO.getDepartmentByName(departmentName);
        if (depForDel != null) {
            nullDepartment.getWorkers().addAll(depForDel.getWorkers());
            departmentDAO.removeDepartment(depForDel);
        }

    }

    private static Worker getWorkOrCreate(Department currentDepartment, Department nullDepartment, String workerName) {
        Worker w = workerDAO.getWorkerByName(workerName);
        if (w == null) {
            w = new Worker(workerName);
            workerDAO.saveWorker(w);
        } else {
            Department oldWorkerDep = departmentDAO.getDepartmentByWorker(w);
            if (oldWorkerDep != null) {
                oldWorkerDep.removeWorker(w);
            }
        }

        if (currentDepartment != null) {
            currentDepartment.addWorker(w);
        } else {
            nullDepartment.addWorker(w);
        }
        return w;
    }

    public static Department getDepOrCreate(String departmentName) {
        Department dp = departmentDAO.getDepartmentByName(departmentName);
        if (dp == null) {
            dp = new Department(departmentName);
            departmentDAO.saveDepartment(dp);
        }

        return dp;
    }

    private static void repeatInfoMessage() {
        logger.info(info);
    }
}
