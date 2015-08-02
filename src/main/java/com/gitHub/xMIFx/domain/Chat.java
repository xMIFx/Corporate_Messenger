package com.gitHub.xMIFx.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vlad on 26.07.2015.
 */
@Entity
@Table(name = "chat")
public class Chat implements Serializable {
    private Long id;
    private String name;
    private Set<Worker> workers;
    private Set<Message> messages;
    private boolean isThereSomeMoreMessages;

    public Chat() {
        this.workers = new HashSet<>();
        this.messages = new HashSet<>();
    }

    public Chat(Long id, String name) {
        this.id = id;
        this.name = name;
        this.workers = new HashSet<>();
        this.messages = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idChat")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "chatworkers",
            joinColumns = {@JoinColumn(name = "idChat")},
            inverseJoinColumns = {@JoinColumn(name = "idWorker")})
    public Set<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "idChat")
    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Transient
    public boolean isThereSomeMoreMessages() {
        return isThereSomeMoreMessages;
    }

    public void setIsThereSomeMoreMessages(boolean isThereSomeMoreMessages) {
        this.isThereSomeMoreMessages = isThereSomeMoreMessages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void addWorker(Worker worker) {
        this.workers.add(worker);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (id != null ? !id.equals(chat.id) : chat.id != null) return false;
        return !(name != null ? !name.equals(chat.name) : chat.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", workers=" + workers.toString() +
                ", messages=" + messages.toString() +
                ", isThereSomeMoreMessages=" + isThereSomeMoreMessages +
                '}';
    }
}
