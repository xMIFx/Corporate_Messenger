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
    private static String info = "some keyWords: \n" +
            "exit - for end writing.\n" +
            "dep - for creation or change department. Example: dep name.\n" +
            "work - for creation worker in selected department. Example: work name.\n" +
            "del_dep - for deleting department by name. Example: del_dep name.\n" +
            "del_work - for deleting worker by name(from department too). Example: del_work name.\n" +
            "del_work_dep - for deleting woker by name only from department. Example: del_work_dep name.\n" +
            "name can't be empty!";
    private static Department dep;

    public static void main(String[] args) {
        logger.info(info);
        Department nullDepartment = new Department("with out department");
        Department currentDepartment = null;
        //for saving
        DepartmentDAO departmentDAO = new DepartmentCollectionDAOImpl();
        departmentDAO.saveDepartment(nullDepartment);

        WorkerDAO workerDAO = new WorkerCollectionDAOImpl();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String line = reader.readLine();
                if (line.equals("exit")) {
                    break;
                } else if (line.startsWith("dep")) {
                    String departmentName = line.substring(4, line.length());
                    if (departmentName == null || departmentName.equals("")) {
                        repeatInfoMessage();
                    } else {
                        currentDepartment = getDep(departmentDAO, departmentName);
                    }
                } else if (line.startsWith("work")) {
                    String workerName = line.substring(5, line.length());
                    if (workerName == null || workerName.equals("")) {
                        repeatInfoMessage();
                    } else {
                        Worker newWorker = getWork(departmentDAO, currentDepartment, nullDepartment, workerDAO, workerName);
                    }
                } else if (line.startsWith("del_dep")) {
                    String departmentName = line.substring(8, line.length());
                    if (departmentName == null || departmentName.equals("")) {
                        repeatInfoMessage();
                    } else {
                        Department depForDel = departmentDAO.getDepartmentByName(departmentName);
                        if (depForDel != null) {
                            departmentDAO.removeDepartment(depForDel);
                        }
                    }
                } else if (line.startsWith("del_work_dep")) {
                    String workerName = line.substring(13, line.length());
                    if (workerName == null || workerName.equals("")) {
                        repeatInfoMessage();
                    } else {
                        Worker workerForDel = workerDAO.getWorkerByName(workerName);
                        Department depForUpdate = departmentDAO.getDepartmentByWorker(workerForDel);
                        if (depForUpdate != null) {
                            depForUpdate.removeWorker(workerForDel);
                        }
                        departmentDAO.updateDepartment(depForUpdate);
                    }
                } else if (line.startsWith("del_work")) {
                    String workerName = line.substring(9, line.length());
                    if (workerName == null || workerName.equals("")) {
                        repeatInfoMessage();
                    } else {
                        Worker workerForDel = workerDAO.getWorkerByName(workerName);
                        Department depForUpdate = departmentDAO.getDepartmentByWorker(workerForDel);
                        if (depForUpdate != null) {
                            depForUpdate.removeWorker(workerForDel);
                        }
                        departmentDAO.updateDepartment(depForUpdate);
                        workerDAO.removeWorker(workerForDel);
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

    private static Worker getWork(DepartmentDAO departmentDAO, Department currentDepartment, Department nullDepartment, WorkerDAO workerDAO, String workerName) {
        Worker w = workerDAO.getWorkerByName(workerName);
        if (w == null) {
            w = new Worker(workerName);
            workerDAO.saveWorker(w);
        }
        Department workerDep = departmentDAO.getDepartmentByWorker(w);
        if (workerDep != null) {
            workerDep.removeWorker(w);
        }

        if (currentDepartment != null) {
            currentDepartment.addWorker(w);
        } else {
            nullDepartment.addWorker(w);
        }
        return w;
    }

    public static Department getDep(DepartmentDAO departmentDAO, String departmentName) {
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
