package com.gitHub.xMIFx.view.servlets.websockets;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Message;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.implementationForDAO.hibernateDAO.WorkerHibernateDAOImpl;
import com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO;
import com.gitHub.xMIFx.services.implementationServices.ChatServiceImpl;
import com.gitHub.xMIFx.services.implementationServices.ConverterObjectToStringJSON;
import com.gitHub.xMIFx.services.interfaces.ChatService;
import com.gitHub.xMIFx.services.interfaces.ConverterObjectToString;
import com.gitHub.xMIFx.view.DTOForView.OnlineWorker;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RecipientOfResponseForChat {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipientOfResponseForChat.class.getName());
    private static final ChatService chatService = new ChatServiceImpl();
    /*private static final WorkerService workerService = new WorkerServiceImpl();*/
    private static final ConverterObjectToString CONVERTER_OBJECT_TO_STRING = new ConverterObjectToStringJSON();

    String getAnswerAboutOnlineUser(Worker worker, boolean online) {
        OnlineWorker onlineWorker = new OnlineWorker(online, worker);
        String answer = CONVERTER_OBJECT_TO_STRING.getMessage(onlineWorker);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(answer);
        }
        return answer;
    }

    String getFirstMessage(Long currentWorkerID) {
        String answer = null;

        List<Worker> workerList = chatService.getAll();
        List<Worker> onlineWorkerList = OnlineWorkerHolder.getOnlineWorkerHolder().getOnlineWorkers();
      /* need List with new Messages*/
        Map<String, DepartmentForView> departmentForViewMap = new HashMap<>();

        for (Worker worker : workerList) {
            if (worker.getId().equals(currentWorkerID)) {
                continue;
            }
            OnlineWorker onlineWorker = new OnlineWorker();
            onlineWorker.setWorker(worker);
            if (onlineWorkerList.contains(worker)) {
                onlineWorker.setOnline(true);
            } else {
                onlineWorker.setOnline(false);
            }
            DepartmentForView departmentForView;
            if (departmentForViewMap.containsKey(worker.getDepartmentName())) {
                departmentForView = departmentForViewMap.get(worker.getDepartmentName());

            } else {
                departmentForView = new DepartmentForView(new Department(worker.getDepartmentName()));
                departmentForViewMap.put(worker.getDepartmentName(), departmentForView);
            }
            departmentForView.addWorker(onlineWorker);
        }
        List<DepartmentForView> departments = new ArrayList<>();
        departments.addAll(departmentForViewMap.values());
        answer = CONVERTER_OBJECT_TO_STRING.getMessage(new HolderForDepartmentView(departments));
        LOGGER.info(answer);
        return answer;
    }

    String getInformationAboutNewMessages(Long currentWorkerID) {
        Worker currentWorker = new Worker();
        currentWorker.setId(currentWorkerID);
        Map<Chat, Integer> countNewMassagesForWorker = chatService.getCountNewMassagesForWorker(currentWorker);
        List<ChatForView> listChatsForView = new ArrayList<>();
        for (Map.Entry<Chat, Integer> pair : countNewMassagesForWorker.entrySet()) {
            listChatsForView.add(new ChatForView(pair.getKey(), pair.getValue()));
        }
        String answer = CONVERTER_OBJECT_TO_STRING.getMessage(new HolderForChatView(listChatsForView));
        return answer;
    }


    String getAnswerAboutNewMessage(Message message) {
        String answer = null;
        Long id = chatService.saveMessage(message);
        if (id != null) {
            message.setId(id);
            answer = CONVERTER_OBJECT_TO_STRING.getMessage(message);
        }
        return answer;
    }

    String getAnswerAboutReadedMessage(Message message) {
        Message answerMessage = chatService.markMessageAsReadDeleted(message);
        String answer = CONVERTER_OBJECT_TO_STRING.getMessage(answerMessage);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(answer);
        }
        return answer;
    }

    String getAnswerAboutChatBetweenTwoWorkers(Set<Worker> workers) {
        Worker workerFrom = null;
        Worker workerTo = null;
        for (Worker worker : workers) {
            if (workerFrom == null) {
                workerFrom = worker;
            } else {

                workerTo = worker;
            }
        }
        String answer = null;
        Chat chatForReturn = chatService.getChatBetweenWorkers(workerFrom, workerTo);
        if (chatForReturn == null) {
            ExceptionForView exceptionForView = new ExceptionForView();
            exceptionForView.setExceptionMessage("Error when getting chat. Try later.");
            answer = CONVERTER_OBJECT_TO_STRING.getMessage(exceptionForView);
        } else {
            answer = CONVERTER_OBJECT_TO_STRING.getMessage(chatForReturn);
        }

        return answer;
    }


    String getAnswerAboutChatByID(Long id) {
        return null;
    }

    static final class DepartmentForView {
        private Department department;
        private List<OnlineWorker> workers;

        public DepartmentForView(Department department) {
            this.department = department;
            this.workers = new ArrayList<>();
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        public List<OnlineWorker> getWorkers() {
            return workers;
        }

        public void setWorkers(List<OnlineWorker> workers) {
            this.workers = workers;
        }

        public void addWorker(OnlineWorker worker) {
            this.workers.add(worker);
        }
    }

    static final class HolderForDepartmentView {
        private List<DepartmentForView> departments;

        public HolderForDepartmentView(List<DepartmentForView> departments) {
            this.departments = departments;
        }

        public List<DepartmentForView> getDepartments() {
            return departments;
        }
    }

    static final class HolderForChatView {
        List<ChatForView> chatsNewMessages;

        public HolderForChatView() {
        }

        public HolderForChatView(List<ChatForView> chatsNewMessages) {
            this.chatsNewMessages = chatsNewMessages;
        }

        public List<ChatForView> getChatsNewMessages() {
            return chatsNewMessages;
        }

        public void setChatsNewMessages(List<ChatForView> chatsNewMessages) {
            this.chatsNewMessages = chatsNewMessages;
        }
    }

    static final class ChatForView {
        Chat chat;
        Integer countNewMess;

        public ChatForView() {
        }

        public ChatForView(Chat chat, Integer countNewMess) {
            this.chat = chat;
            this.countNewMess = countNewMess;
        }

        public Chat getChat() {
            return chat;
        }

        public void setChat(Chat chat) {
            this.chat = chat;
        }

        public Integer getCountNewMess() {
            return countNewMess;
        }

        public void setCountNewMess(Integer countNewMess) {
            this.countNewMess = countNewMess;
        }
    }


}
