package com.gitHub.xMIFx.repositories.interfacesForDAO;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;

import java.util.List;

public interface DepartmentDAO {

    Long save(Department department);

    Department getById(Long id);

    Department getByName(String name);

    Department getByWorker(Worker worker);

     List<Department> getAll();

    List<Department> getAllWithoutWorkers();

    boolean remove(Department department);

    boolean update(Department department);

    List<Long> getByWorkers(List<Worker> workerList);

    List<Department> getByListIDs(List<Long> listID);

    List<Department> findByName(String name);
}
