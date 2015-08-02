package com.gitHub.xMIFx.view.DTOForView;

import com.gitHub.xMIFx.domain.Worker;

/**
 * Created by Vlad on 25.07.2015.
 */
public class OnlineWorker {

    private boolean online;
    private Worker worker;
    public OnlineWorker() {

    }

    public OnlineWorker(boolean online, Worker worker) {
        this.online = online;
        this.worker = worker;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

}
