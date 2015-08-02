package com.gitHub.xMIFx.services.interfaces;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Message;
import com.gitHub.xMIFx.domain.Worker;

import java.util.Map;

/**
 * Created by Vlad on 31.07.2015.
 */
public interface ChatService {
    Chat getChatBetweenWorkers(Worker workerFrom, Worker workerTo);
    Map<Chat,Integer> getCountNewMassagesForWorker(Worker worker);
    Long saveMessage(Message message);
}
