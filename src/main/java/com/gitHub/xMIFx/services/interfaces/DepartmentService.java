package com.gitHub.xMIFx.services.interfaces;

        import com.gitHub.xMIFx.services.FinderType;

/**
 * Created by Vlad on 11.07.2015.
 */
public interface DepartmentService extends MainService{
    String find(FinderType finderType, String searchValue);

    String getAll();

    String create(String jsonText);

    String update(String jsonText);

    String getByID(Long id);

    String deleteByID(Long id);
}
