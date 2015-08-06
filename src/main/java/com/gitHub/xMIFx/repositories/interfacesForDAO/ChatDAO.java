package com.gitHub.xMIFx.repositories.interfacesForDAO;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Message;
import com.gitHub.xMIFx.domain.Worker;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ChatDAO {

    Long saveNewChat(Chat chat);
    Chat getChatById(Long chatID);
    List<Worker> getWorkersFromChat(Long chatID);
    List<Worker> getAll();
    Chat getChatBetweenWorkers(Worker workerFrom, Worker workerTo);
    List<Chat> getLastChatsByWorkerID(Long id);
    List<Chat> getAllBigChatsByWorker(Worker worker);
    Chat getMoreMessagesInChat(Long chatID, int countMessageAlreadyInChat, Date minDateInChat, int howMuchNeed, Worker worker);
    Long saveMessage(Message mes);
    Message readDeleteByWorkerToInMessage(Message mes);
    Map<Chat,Integer> getCountNewMassagesForWorker(Worker worker);

}
