package com.gitHub.xMIFx.services.interfaces;

        import com.gitHub.xMIFx.services.FinderType;

/**
 * Created by Vlad on 11.07.2015.
 */
public interface DepartmentService {
    String find(FinderType finderType, String searchValue);

    String getAll();

    String create(String name, String login, String password);

    String update(Long id, String name, String login, String password, int objVersion, String depName);

    String getByID(Long id);

    String deleteByID(Long id);
}
