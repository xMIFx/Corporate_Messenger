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

    public Department() {

    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
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
