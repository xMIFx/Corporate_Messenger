package com.gitHub.xMIFx.services.interfaces;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.services.FinderType;

import java.util.List;

/**
 * Created by Vlad on 10.07.2015.
 */
public interface WorkerService extends MainService{

    String find(FinderType finderType, String searchValue);

    String getAllAnswer();

    String create(String name, String login, String password);

    String update(Long id, String name, String login, String password, int objVersion, String depName);

    String getByIDAnswer(Long id);

    String deleteByID(Long id);

    Worker getByLoginPassword(String login, String password);

    Worker getByID(Long id);
}
