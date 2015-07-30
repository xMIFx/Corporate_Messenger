package com.gitHub.xMIFx.domain;

import org.omg.CORBA.LongHolder;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Vlad on 26.07.2015.
 */
@Entity
@Table(name = "message")
public class Message {

    private Long id;
    private Long chatID;
    private Worker workerFrom;
    private String message;
    private Date dateMessage;
    private Set<WorkerTo> workersTo;


    public Message() {
        this.workersTo = new HashSet<>();
    }

    public Message(Long id, Long chatID, String message, Date dateMessage, Set<WorkerTo> workersTo) {
        this.id = id;
        this.chatID = chatID;
        this.message = message;
        this.dateMessage = new Date(dateMessage.getTime());
        ;
        this.workersTo = workersTo;
    }


    public Message(Long id, Long chatID, Worker workerFrom, String message, Date dateMessage) {
        this.id = id;
        this.chatID = chatID;
        this.workerFrom = workerFrom;
        this.message = message;
        this.dateMessage = new Date(dateMessage.getTime());
        ;
        this.workersTo = new HashSet<>();
    }

    public Message(Long chatID, Worker workerFrom, String message, Date dateMessage) {
        this.chatID = chatID;
        this.workerFrom = workerFrom;
        this.message = message;
        this.dateMessage = new Date(dateMessage.getTime());
        ;
        this.workersTo = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "idChat")
    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    @ManyToOne
    public Worker getWorkerFrom() {
        return workerFrom;
    }

    public void setWorkerFrom(Worker workerFrom) {
        this.workerFrom = workerFrom;
    }

    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateMessage")
    public Date getDateMessage() {
        return new Date(dateMessage.getTime());
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = new Date(dateMessage.getTime());
    }

    @Transient
   /* @ManyToMany
    @JoinTable(name = "messagetoworker",
            joinColumns = {@JoinColumn(name = "idMessage")},
            inverseJoinColumns = {@JoinColumn(name = "idWorkerTo")})*/
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

        return !(id != null ? !id.equals(message.id) : message.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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
