package com.gitHub.xMIFx.services.interfaces;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.services.FinderType;

import java.util.List;


public interface DepartmentService {
    List<Department> find(FinderType finderType, String searchValue);

    List<Department> getAll();

    List<Department> getAllWithoutWorkers();

    List<Department> create(Department department);

    List<Department> update(Department department);

    Department getByID(Long id);

    boolean delete(Department department);
}
