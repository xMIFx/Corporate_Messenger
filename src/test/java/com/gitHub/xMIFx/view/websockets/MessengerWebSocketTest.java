package com.gitHub.xMIFx.view.websockets;

import com.gitHub.xMIFx.domain.Chat;
import com.gitHub.xMIFx.domain.Worker;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by bukatinvv on 30.07.2015.
 */
public class MessengerWebSocketTest {
    @Test
    public void readJsonForChat() {
        String msg = "{\"messages\":[],\"workers\":[{\"id\":1,\"name\":\"From\",\"login\":\"From\"},{\"id\":2,\"name\":\"To\"}]}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Chat chat = objectMapper.readValue(msg, Chat.class);
            System.out.println(chat);
            JsonFactory factory = objectMapper.getJsonFactory();
            JsonParser parser = factory.createJsonParser(msg);
            JsonNode actualObj = objectMapper.readTree(parser);
            System.out.println(actualObj);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
