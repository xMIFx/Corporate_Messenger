package com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Message;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.interfacesForDAO.ChatDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ChatHibernateDAOImpl implements ChatDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerHibernateDAOImpl.class.getName());
    private static SessionFactory sessionFact = HibernateUtil.getSessionFactory();

    @Override
    public Long saveNewChat(Chat chat) {
        if (chat.getId() != null) {
            return chat.getId();
        }
        Transaction tx = null;
        Long idForReturn = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                idForReturn = (Long) session.save(Chat.class.getName(), chat);
                chat.setId(idForReturn);
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
        }
        return idForReturn;
    }


    @Override
    public Chat getChatById(Long chatID) {
        Chat chat = null;
        try (Session session = sessionFact.openSession()) {
            chat = session.get(Chat.class, chatID);
        }
        return chat;
    }

    @Override
    public List<Worker> getWorkersFromChat(Long chatID) {
        List<Worker> workerList = null;
        try (Session session = sessionFact.openSession()) {
            Chat chat = session.get(Chat.class, chatID);
            workerList = new ArrayList<>(chat.getWorkers());
        }
        return workerList;
    }

    @Override
    public List<Worker> getAll() {
        String getAllSql = "SELECT \n" +
                "w.id\n" +
                ", w.name\n" +
                ", w.login\n" +
                ", w.password\n" +
                ", w.objectVersion\n" +
                ", w.admin\n" +
                ",IFNULL(dep.name, \"Without department\") departmentName\n" +
                "FROM corporate_messenger.workers w\n" +
                "left join corporate_messenger.departmentworkers depwork\n" +
                "\tleft join corporate_messenger.departments dep\n" +
                "\t on depwork.iddepartment = dep.id\n" +
                "on w.id = depwork.idworker";

        List<Worker> workerList = null;
        try (Session session = sessionFact.openSession()) {
            Query query = session.createSQLQuery(getAllSql)
                    .addScalar("id", new LongType())
                    .addScalar("name", new StringType())
                    .addScalar("login", new StringType())
                    .addScalar("password", new StringType())
                    .addScalar("objectVersion", new IntegerType())
                    .addScalar("admin", new BooleanType())
                    .addScalar("departmentName", new StringType());
            query.setResultTransformer(Transformers.aliasToBean(Worker.class));
            workerList = (List<Worker>) query.list();
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }
        if (workerList == null) {
            workerList = new ArrayList<>();
        }
        return workerList;
    }

    @Override
    public Chat getChatBetweenWorkers(Worker workerFrom, Worker workerTo) {
        String sqlGetBetweenTwoWorkers = "Select DISTINCT\n" +
                "cw.idChat\n" +
                "from\n" +
                "corporate_messenger.chatworkers cw\n" +
                "inner join\n" +
                "\t(SELECT \n" +
                "\t\tchatworkers.idChat\n" +
                "\t\t,Count(DISTINCT chatworkers.idWorker)\n" +
                "\t\t\tFROM corporate_messenger.chatworkers\n" +
                "\t\t\twhere chatworkers.idWorker = :idFirstWorkerParam\n" +
                "\t\t\t\t\tor chatworkers.idWorker = :idSecondWorkerParam\n" +
                "\t\t\tGroup by chatworkers.idChat\n" +
                "\t\t\tHaving Count(DISTINCT chatworkers.idWorker) = 2) finder\n" +
                " on\n" +
                " cw.idChat = finder.idChat\n" +
                " group by cw.idChat\n" +
                " having  Count(DISTINCT cw.idWorker) = 2";
        Transaction tx = null;
        Chat chat = null;
        try (Session session = sessionFact.openSession()) {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sqlGetBetweenTwoWorkers).addScalar("idChat", new LongType());
            query.setParameter("idFirstWorkerParam", workerFrom.getId());
            query.setParameter("idSecondWorkerParam", workerTo.getId());
            Long chatID = (Long) query.uniqueResult();
            if (chatID == null) {
                chat = new Chat();
                chat.setName("BetweenTwoWorkers");
                chat.addWorker(workerFrom);
                chat.addWorker(workerTo);
                chatID = (Long) session.save(chat);
                session.flush();
                session.evict(chat);
                chat = null;
            }
            chat = session.get(Chat.class, chatID);
             /*it works but need something else or else logic*/
            tx.commit();
        } catch (Throwable e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.error("Some SQL error: ", e);
        }
        return chat;
    }

    @Override
    public List<Chat> getLastChatsByWorkerID(Long id) {
        return null;
    }

    @Override
    public List<Chat> getAllBigChatsByWorker(Worker worker) {
        return null;
    }

    @Override
    public Chat getMoreMessagesInChat(Long chatID, int countMessageAlreadyInChat, Date minDateInChat, int howMuchNeed, Worker worker) {
        return null;
    }

    @Override
    public Long saveMessage(Message mes) {
        if (mes.getId() != null) {
            return mes.getId();
        }
        Transaction tx = null;
        Long idForReturn = null;
        try (Session session = sessionFact.openSession()) {
            try {
                tx = session.beginTransaction();
                idForReturn = (Long) session.save(Message.class.getName(), mes);
                mes.setId(idForReturn);
                tx.commit();
            } catch (Throwable e) {
                if (tx != null) {
                    tx.rollback();
                }
                LOGGER.error("Some SQL exception", e);
            }
        }
        return idForReturn;
    }

    @Override
    public Message readDeleteByWorkerToInMessage(Message mes) {
        if (mes.getId() == null) {
            return null;
        }
        String sqlUpdate = "UPDATE corporate_messenger.messagetoworker SET newMessage = :newMessageParam, markForDelete = :deleteMessageParam " +
                "WHERE idMessage = :idMessageParam and idWorkerTo = :workerIdParam ;";
        Transaction tx = null;
        Message savedMessage = null;
        try (Session session = sessionFact.openSession()) {
            tx = session.beginTransaction();
            savedMessage = session.get(Message.class, mes.getId());
            for (Worker worker : mes.takeWorkerForMessage()) {
                Query query = session.createSQLQuery(sqlUpdate);
                query.setParameter("newMessageParam", mes.isNewForWorker(worker));
                query.setParameter("deleteMessageParam", mes.isDeleteForWorker(worker));
                query.setParameter("idMessageParam", mes.getId());
                query.setParameter("workerIdParam", worker.getId());
                query.executeUpdate();
            }
            session.flush();
            session.refresh(savedMessage);
            tx.commit();
            LOGGER.info("message ", savedMessage);
        } catch (Throwable e) {
            LOGGER.error("Some sql excaption: ", e);
            if (tx != null) {
                tx.rollback();
            }
        }
        return savedMessage;
    }

    @Override
    public Map<Chat, Integer> getCountNewMassagesForWorker(Worker worker) {
        String sqlGetCount = "Select \n" +
                "m.idChat\n" +
                ",c.name\n" +
                ",COUNT(DISTINCT m.idMessage) newMessages\n" +
                ",MAX(m.idWorkerFrom) idWorkerFrom\n" +
                "from\n" +
                "(SELECT \n" +
                "mw.idMessage\n" +
                ",mw.idWorkerTo \n" +
                "FROM corporate_messenger.messagetoworker mw\n" +
                "where mw.newMessage = 1\n" +
                "and idWorkerTo = :idWorkerParam) newMes\n" +
                "inner join message m \n" +
                "on newMes.idMessage =  m.idMessage\n" +
                "inner join chat c\n" +
                "on m.idChat = c.idChat\n" +
                "group by m.idChat\n" +
                ", c.name;";
        Map<Chat, Integer> chatMap = new HashMap<>();
        try (Session session = sessionFact.openSession()) {
            Query query = session.createSQLQuery(sqlGetCount)
                    .addScalar("idChat", new LongType())
                    .addScalar("name", new StringType())
                    .addScalar("newMessages", new IntegerType())
                    .addScalar("idWorkerFrom", new LongType());
            query.setParameter("idWorkerParam", worker.getId());
            List<Object[]> rows = query.list();
            for (Object[] row : rows) {
                Chat chat = new Chat((Long) row[0], (String) row[1]);
                Worker workerFrom = new Worker();
                workerFrom.setId((Long) row[3]);
                chat.addWorker(workerFrom);
                Integer countNewMes = (Integer) row[2];
                chatMap.put(chat, countNewMes);
            }
        } catch (Throwable e) {
            LOGGER.error("Some SQL exception", e);
        }

        return chatMap;
    }
}
