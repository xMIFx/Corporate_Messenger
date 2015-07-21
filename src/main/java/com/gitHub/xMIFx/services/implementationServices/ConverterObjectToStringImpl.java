package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.services.ObjectConvertingType;
import com.gitHub.xMIFx.services.interfaces.ConverterObjectToString;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Vlad on 17.07.2015.
 */
public final class ConverterObjectToStringImpl implements ConverterObjectToString {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterObjectToStringImpl.class.getName());

    @Override
    public <T> String getMessage(T value, ObjectConvertingType convertingType) {
        String message = null;
        try {
            switch (convertingType) {
                case JSON: {
                    message = getJSONMessage(value);
                    break;
                }
                case XML: {
                    message = getXMLMessage(value);
                    break;
                }
            }
        } catch (JAXBException e) {
            LOGGER.error("Some jaxB exception", e);
        } catch (IOException e) {
            LOGGER.error("Some jackson exception", e);
        }
        return message;
    }

    private <T> String getXMLMessage(T value) throws JAXBException {
        JAXBContext jaxbRootContext = null;
        StringWriter writer = new StringWriter();
        jaxbRootContext = JAXBContext.newInstance(value.getClass());
        Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(value, writer);
        LOGGER.info(writer.toString());
        return writer.toString();
    }


    private <T> String getJSONMessage(T value) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(value);
    }


}
