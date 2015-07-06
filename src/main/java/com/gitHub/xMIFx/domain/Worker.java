package com.gitHub.xMIFx.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;

/**
 * Created by Vlad on 23.06.2015.
 */
@XmlRootElement
public class Worker implements Externalizable {
    private String name;
    private String password;
    private String login;
    private Long id;

    public Worker() {

    }

    public Worker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public int calculateSalary(int workingTime, int payment) {
        if (workingTime < 0) {
            throw new IllegalArgumentException("working time can't be less 0");
        }
        if (payment <= 0) {
            throw new IllegalArgumentException("payment  can't be less or =  0");
        }
        return workingTime * payment;

    }

    @Override
    public String toString() {

        return String.format("%d-%s: [%s]", id, name, login);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Worker worker = (Worker) o;

        if (name != null ? !name.equals(worker.name) : worker.name != null) return false;
        return !(id != null ? !id.equals(worker.id) : worker.id != null);

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
        /*out.writeUTF(this.login);
        out.writeUTF(this.password);*/
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readLong();
        this.name = in.readUTF();
       /* this.login = in.readUTF();
        this.password = in.readUTF();*/
    }
}
