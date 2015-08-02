package com.gitHub.xMIFx.view.servlets.websockets;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Message;
import com.gitHub.xMIFx.domain.Worker;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vlad on 24.07.2015.
 */
@ServerEndpoint(value = "/messenger.do/chat", configurator = EndpointConfiguratorChat.class)
public class MessengerWebSocket {
    private static final String COOKIE_FOR_WEBSOCKET = "currentWorker";
    private static final Logger LOGGER = LoggerFactory.getLogger(MessengerWebSocket.class.getName());
    private static final RecipientOfResponseForChat answerGetter = new RecipientOfResponseForChat();
    private static Map<Long, Session> usersWebSocketSession = new ConcurrentHashMap<Long, Session>();

    private EndpointConfig config;


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.config = config;
        usersWebSocketSession.put((Long) config.getUserProperties().get(COOKIE_FOR_WEBSOCKET), session);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("WebSocket is open");
        }

        sendMessageTo(session, answerGetter.getFirstMessage((Long) config.getUserProperties().get(COOKIE_FOR_WEBSOCKET)));
        sendMessageTo(session, answerGetter.getInformationAboutNewMessages((Long) config.getUserProperties().get(COOKIE_FOR_WEBSOCKET)));

    }

    @OnClose
    public void onClose(Session session) {
        usersWebSocketSession.remove(this.config.getUserProperties().get(COOKIE_FOR_WEBSOCKET));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("WebSocket is closed");
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace(); //ignored for first time
        LOGGER.error("WebSocket error: ", t);
    }

    @OnMessage
    public void echoTextMessage(Session session, String msg) {
        LOGGER.info(msg);
        parseMessageFromJson(session, msg);

    }

    public void sendMessageTo(Session session, String jsonStr) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Send json to websocket:  " + jsonStr);
        }
        try {
            session.getBasicRemote().sendText(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSendMessageAboutOnline(Worker worker, boolean online) {
        String json = answerGetter.getAnswerAboutOnlineUser(worker, online);
        for (Map.Entry<Long, Session> pair : usersWebSocketSession.entrySet()) {
            try {
                pair.getValue().getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    void parseMessageFromJson(Session session, String jsonStr) {
        String answer = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (jsonStr.contains("chatID")) {
                Message message = objectMapper.readValue(jsonStr, Message.class);
                answer = answerGetter.getAnswerAboutNewMessage(message);
                if (answer == null) {
                    answer = jsonStr;
                } else {
                    for (Worker worker : message.takeWorkerForMessage()) {
                        if (usersWebSocketSession.containsKey(worker.getId())) {
                            sendMessageTo(usersWebSocketSession.get(worker.getId()), answer);
                        }

                    }

                }
            } else if (jsonStr.contains("messages")) {
                Chat chat = objectMapper.readValue(jsonStr, Chat.class);
                if (chat.getId() != null) {
                    answer = answerGetter.getAnswerAboutChatByID(chat.getId());
                } else {
                    answer = answerGetter.getAnswerAboutChatBetweenTwoWorkers(chat.getWorkers());
                }
            } else {
                /*NOP*/
            }
        } catch (Throwable e) {
            LOGGER.error("error parse json", e);
        }
        sendMessageTo(session, answer);
    }

}


