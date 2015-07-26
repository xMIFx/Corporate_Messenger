package com.gitHub.xMIFx.view.domainForView;

import com.gitHub.xMIFx.domain.Worker;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Vlad on 25.07.2015.
 */
public class OnlineWorkerHolder {
    private static OnlineWorkerHolder onlineWorkerHolder = new OnlineWorkerHolder();
    private List<Worker> onlineWorkers;

    private OnlineWorkerHolder() {
        this.onlineWorkers = new CopyOnWriteArrayList<>();
    }

    public static OnlineWorkerHolder getOnlineWorkerHolder() {
        return onlineWorkerHolder;
    }

    public List<Worker> getOnlineWorkers() {
        return this.onlineWorkers;
    }

    public void add(Worker worker) {
        this.onlineWorkers.add(worker);
    }

    public void remove(Worker worker) {
        this.onlineWorkers.remove(worker);
    }
}
