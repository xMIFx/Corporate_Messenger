package com.gitHub.xMIFx.repositories.DTO;

import com.gitHub.xMIFx.domain.Worker;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Vlad on 30.06.2015.
 */
@XmlRootElement
public class WorkersHolder {
    private List<Worker> workers;

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}
