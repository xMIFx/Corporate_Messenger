package com.gitHub.xMIFx.view.servlets.websockets;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Worker;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
        //need remove current worker. later
        sendMessage(session, answerGetter.getFirstMessage((Long) config.getUserProperties().get(COOKIE_FOR_WEBSOCKET)));
       /* sendMessageAboutCountNewMessages(session);*/
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
        sendMessage(session, answerGetter.parseMessageFromJson(msg));


      /*  parseMessageFromJson(session, msg);*/

    }

    public void sendMessage(Session session, String jsonStr) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Send json to websocket:  " + jsonStr);
        }
        try {
            for (Map.Entry<Long, Session> pair : usersWebSocketSession.entrySet()) {
                pair.getValue().getBasicRemote().sendText(jsonStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSendMessageAboutOnline(Worker worker, boolean online) {
        String json = null;
        json = answerGetter.getAnswerAboutOnlineUser(worker, online);
        for (Map.Entry<Long, Session> pair : usersWebSocketSession.entrySet()) {
            try {
                pair.getValue().getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}


