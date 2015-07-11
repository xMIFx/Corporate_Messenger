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
    private int objectVersion;
    private String departmentName;


    public Worker() {

    }

    public Worker(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        name = name.trim();
        if (name.equals("")) {
            throw new IllegalArgumentException("name can't be empty");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("name length can't be < 3");
        }
        if (!name.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]*")) {
            throw new IllegalArgumentException("wrong symbols");
        }
        this.name = name;
    }

    public Worker(Long id, String name, String login, String password) {
        validateAllStringFields(name, login, password);
        this.name = name;
        this.password = password;
        this.login = login;
        this.id = id;
    }

    public Worker(Long id, String name, String login, String password, int objectVersion, String departmentName) {
        validateAllStringFields(name, login, password);
        this.name = name;
        this.password = password;
        this.login = login;
        this.id = id;
        this.objectVersion = objectVersion;
        this.departmentName = departmentName;
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
        if (name.length() < 3) {
            throw new IllegalArgumentException("name length can't be < 3");
        }
        if (!name.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]*")) {
            throw new IllegalArgumentException("wrong symbols");
        }

        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("password can't be null");
        }
        password = password.trim();
        if (password.equals("")) {
            throw new IllegalArgumentException("password can't be empty");
        }
        if (password.length() < 3) {
            throw new IllegalArgumentException("password length can't be < 3");
        }
        if (!password.matches("[A-Za-z0-9]*")) {
            throw new IllegalArgumentException("wrong symbols");
        }
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    @XmlElement
    public void setLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("login can't be null");
        }
        login = login.trim();
        if (login.equals("")) {
            throw new IllegalArgumentException("login can't be empty");
        }
        if (!login.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]*")) {
            throw new IllegalArgumentException("wrong symbols");
        }
        this.login = login;
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

    public String getDepartmentName() {
        return departmentName;
    }

    @XmlElement
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    private void validateAllStringFields(String name, String login, String password) {
        if (name == null || login == null || password == null) {
            throw new IllegalArgumentException("name/login/password can't be null");
        }
        name = name.trim();
        login = login.trim();
        password = password.trim();
        if (name.equals("") || login.equals("") || password.equals("")) {
            throw new IllegalArgumentException("name/login/password can't be empty");
        }
        if (name.length() < 3 || password.length() < 3) {
            throw new IllegalArgumentException("name/password length can't be < 3");
        }
        if (!name.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]*")
                || !password.matches("[A-Za-z0-9]*")
                || !login.matches("^[a-zA-Z]+[A-Za-z0-9\\s\\.]*")) {
            throw new IllegalArgumentException("wrong symbols");
        }
    }
}
