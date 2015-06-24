package com.gitHub.xMIFx.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 23.06.2015.
 */
public class Department {
    private String name;
    private Long id;
    private List<Worker> workers;

    public Department() {

    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Worker> getWorkers() {
        if (this.workers == null) {
            this.workers = new ArrayList<Worker>();
        }
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public void addWorker(Worker worker) {
        if (this.workers == null) {
            this.workers = new ArrayList<Worker>();
        }
        this.workers.add(worker);
    }

    public void removeWorker(Worker worker) {
        if (this.workers == null || !this.workers.contains(worker)) {/*NOP*/} else {
            this.workers.remove(worker);
        }

    }

    @Override
    public String toString() {
        return  "'" + name + '\'' +
                ", " + id +
                ", workers=" + workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
