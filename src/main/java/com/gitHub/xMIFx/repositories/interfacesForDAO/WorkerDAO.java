package com.gitHub.xMIFx.repositories.interfacesForDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;

import java.util.List;

public interface WorkerDAO {

    Long save(Worker worker);

    Worker getById(Long id);

    Worker getByName(String name);

    Worker getByLoginPassword(String login, String pass);

    List<Worker> getAll();

    List<Worker> getByDepartment(Department department);

    boolean remove(Worker worker);

    boolean update(Worker worker);

    List<Worker> findByName(String name);

    List<Worker> findByLogin(String login);

    List<Worker> findByDepartmentName(String depName);

}
