package com.gitHub.xMIFx.services.interfaces;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.services.FinderType;

import java.util.List;

public interface WorkerService {

    List<Worker> find(FinderType finderType, String searchValue);

    List<Worker> getAll();

    Long create(Worker worker);

    boolean update(Worker worker);

    boolean delete(Worker worker);

    Worker getByLoginPassword(String login, String password);

    Worker getByID(Long id);
}
