package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.services.interfaces.ConverterObjectToString;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ConverterObjectToStringJSON implements ConverterObjectToString {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterObjectToStringXML.class.getName());

    @Override
    public <T> String getMessage(T value) {
        String message = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.writeValueAsString(value);
        } catch (IOException e) {
            LOGGER.error("exception when create json", e);
        }
        return message;

    }
}
