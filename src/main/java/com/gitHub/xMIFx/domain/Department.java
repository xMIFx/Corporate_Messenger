package com.gitHub.xMIFx.domain;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 23.06.2015.
 */
@XmlRootElement
@Entity
@Table(name = "departments")
public class Department implements Serializable {

    private String name;
    private Long id;
    private List<Worker> workers;
    private int objectVersion;
    private int workersCount;

    public Department() {
        this.workers = new ArrayList<>();
    }

    public Department(String name) {
        setName(name);
        this.workers = new ArrayList<>();
    }

    @Column(name = "name", unique = true, length = 45)
    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        //use some library
        name = StringUtils.trim(name);
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name can't be null");
        }
        if (!name.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]{1,45}$")) {
            throw new IllegalArgumentException("wrong symbols");
        }
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("id can'be null or < 0");
        }
        this.id = id;
    }


    @Fetch(FetchMode.SELECT)
    @OneToMany
    @JoinTable(name = "departmentworkers",
            joinColumns = {@JoinColumn(name = "iddepartment")},
            inverseJoinColumns = {@JoinColumn(name = "idworker")})
    public List<Worker> getWorkers() {
        return workers;
    }

    @XmlElement
    public void setWorkers(List<Worker> workers) {
        if (workers != null) {
            this.workers = workers;
            this.workersCount = this.workers.size();
        }
    }

    @Column(name = "objectVersion")
    public int getObjectVersion() {
        return objectVersion;
    }

    @XmlElement
    public void setObjectVersion(int objectVersion) {
        if (objectVersion < 0) {
            throw new IllegalArgumentException("objectVersion can't be  <0");
        }

        this.objectVersion = objectVersion;
    }

    @Transient
    public int getWorkersCount() {
        return workersCount;
    }

    @XmlElement
    public void setWorkersCount(int workersCount) {
        if (workersCount < 0) {
            throw new IllegalArgumentException("workersCount can't be <0");
        }
        this.workersCount = workersCount;
    }

    public void addWorker(Worker worker) {
        this.workers.add(worker);
        this.workersCount = workers.size();
    }

    public void removeWorker(Worker worker) {
        if (this.workers.contains(worker)) {
            this.workers.remove(worker);
            this.workersCount = workers.size();
        }
    }

    @Override
    public String toString() {
        return id +
                " - '" + name + '\'' +
                ": workers=" + workers + "\n";
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

    /*@Override*/
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.id);
        out.writeUTF(this.name);
        if (this.workers != null) {
            out.writeInt(this.workers.size());
            for (Worker worker : workers) {
                worker.writeExternal(out);
            }
        } else {
            out.writeInt(0);
        }
    }

    /*@Override*/
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readLong();
        this.name = in.readUTF();
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            Worker readWorker = new Worker();
            readWorker.readExternal(in);
            this.addWorker(readWorker);
        }
    }

}
