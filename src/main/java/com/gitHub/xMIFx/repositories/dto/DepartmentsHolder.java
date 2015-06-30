package com.gitHub.xMIFx.repositories.dto;

import com.gitHub.xMIFx.domain.Department;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Vlad on 30.06.2015.
 */
@XmlRootElement
public class DepartmentsHolder {
    private List<Department> departments;

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
