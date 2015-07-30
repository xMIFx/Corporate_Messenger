package com.gitHub.xMIFx.domain;

import java.util.*;

/**
 * Created by Vlad on 26.07.2015.
 */
public class Message {

    private int id;
    private int chatID;
    private Worker workerFrom;
    private String message;
    private Date dateMessage;
    private Set<WorkerTo> workersTo;


    public Message() {
        this.workersTo = new HashSet<>();
    }

    public Message(int id, int chatID, String message, Date dateMessage, Set<WorkerTo> workersTo) {
        this.id = id;
        this.chatID = chatID;
        this.message = message;
        this.dateMessage = new Date(dateMessage.getTime());;
        this.workersTo = workersTo;
    }


    public Message(int id, int chatID, Worker workerFrom, String message, Date dateMessage) {
        this.id = id;
        this.chatID = chatID;
        this.workerFrom = workerFrom;
        this.message = message;
        this.dateMessage = new Date(dateMessage.getTime());;
        this.workersTo = new HashSet<>();
    }

    public Message(int chatID, Worker workerFrom, String message, Date dateMessage) {
        this.chatID = chatID;
        this.workerFrom = workerFrom;
        this.message = message;
        this.dateMessage = new Date(dateMessage.getTime());;
        this.workersTo = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public Worker getWorkerFrom() {
        return workerFrom;
    }

    public void setWorkerFrom(Worker workerFrom) {
        this.workerFrom = workerFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateMessage() {
        return  new Date(dateMessage.getTime());
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = new Date(dateMessage.getTime());
    }

    public Set<WorkerTo> getWorkersTo() {
        return workersTo;
    }

    public void setWorkersTo(Set<WorkerTo> workersTo) {
        this.workersTo = workersTo;
    }

    public void addWorkerTo(Worker worker, boolean isNewMassage, boolean isDeleted) {

        if (worker.getId().equals(workerFrom.getId())) {
            workersTo.add(new WorkerTo(worker, isNewMassage, isDeleted));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", chatID=" + chatID +
                ", workerFrom=" + workerFrom +
                ", message='" + message + '\'' +
                ", dateMessage=" + dateMessage +
                ", workersTo=" + workersTo +
                '}';
    }

    private static class WorkerTo {
        private Worker workerTo;
        private boolean itNewMessage;
        private boolean itDeleted;

        public WorkerTo(Worker workerTo, boolean itNewMessage, boolean itDeleted) {
            this.workerTo = workerTo;
            this.itNewMessage = itNewMessage;
            this.itDeleted = itDeleted;
        }

        public Worker getUserTo() {
            return workerTo;
        }

        public void setUserTo(Worker userTo) {
            this.workerTo = userTo;
        }

        public boolean isItNewMessage() {
            return itNewMessage;
        }

        public void setItNewMessage(boolean itNewMessage) {
            this.itNewMessage = itNewMessage;
        }

        public boolean isItDeleted() {
            return itDeleted;
        }

        public void setItDeleted(boolean itDeleted) {
            this.itDeleted = itDeleted;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            WorkerTo usersTo = (WorkerTo) obj;

            return !(workerTo != null ? !workerTo.equals(usersTo.workerTo) : usersTo.workerTo != null);

        }

        @Override
        public int hashCode() {
            return workerTo != null ? workerTo.hashCode() : 0;
        }
    }
}
