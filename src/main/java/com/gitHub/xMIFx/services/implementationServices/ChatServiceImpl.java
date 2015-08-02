package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Message;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.ChatHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.ChatDAO;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import com.gitHub.xMIFx.services.interfaces.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Vlad on 31.07.2015.
 */
public class ChatServiceImpl implements ChatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerServiceImpl.class.getName());
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();
    private static ChatDAO chatDAO = abstractFactoryForDAOf.getChatDAOImpl();

    @Override
    public Chat getChatBetweenWorkers(Worker workerFrom, Worker workerTo) {
        return  chatDAO.getChatBetweenWorkers(workerFrom, workerTo);
    }

    @Override
    public Map<Chat, Integer> getCountNewMassagesForWorker(Worker worker) {
        return chatDAO.getCountNewMassagesForWorker(worker);
    }

    @Override
    public Long saveMessage(Message message) {
        return chatDAO.saveMessage(message);
    }
}
