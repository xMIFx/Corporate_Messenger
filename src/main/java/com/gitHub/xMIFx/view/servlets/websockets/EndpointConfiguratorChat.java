package com.gitHub.xMIFx.view.servlets.websockets;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;

/**
 * Created by Vlad on 24.07.2015.
 */
public class EndpointConfiguratorChat extends ServerEndpointConfig.Configurator {

    private static final String COOKIE_NAME = "worker";
    private static final String COOKIE_FOR_WEBSOCKET = "currentWorker";

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        List<String> cookiesList = request.getHeaders().get("cookie");
        Long currentWorkerID = null;
        for (String cookies : cookiesList) {
            String[] cookiesArray = cookies.split(";");
            for (String cook : cookiesArray) {
                cook = cook.trim();
                if (cook.startsWith(COOKIE_NAME)) {
                    currentWorkerID = Long.valueOf(cook.substring(cook.lastIndexOf("=") + 1, cook.length()));
                }
            }
        }
        if (currentWorkerID == null) {
            currentWorkerID = 0L;
        }
        config.getUserProperties().put(COOKIE_FOR_WEBSOCKET, currentWorkerID);
    }
}
