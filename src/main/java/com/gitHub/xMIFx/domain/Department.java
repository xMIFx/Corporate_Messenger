package com.gitHub.xMIFx.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 23.06.2015.
 */
@XmlRootElement
public class Department implements Externalizable {

    private String name;
    private Long id;
    private List<Worker> workers;
    private int objectVersion;
    private int workersCount;

    public Department() {

    }

    public Department(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        name = name.trim();
        if (name.equals("")) {
            throw new IllegalArgumentException("name can't be empty");
        }
        if (!name.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]*")) {
            throw new IllegalArgumentException("wrong symbols");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        name = name.trim();
        if (name.equals("")) {
            throw new IllegalArgumentException("name can't be empty");
        }
        if (!name.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]*")) {
            throw new IllegalArgumentException("wrong symbols");
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("id can'be null or <0");
        }
        this.id = id;
    }

    public List<Worker> getWorkers() {
        if (this.workers == null) {
            this.workers = new ArrayList<Worker>();
        }
        return workers;
    }

    @XmlElement
    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

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

    @Override
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

    @Override
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
