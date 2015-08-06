package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.services.interfaces.ConverterObjectToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public final class ConverterObjectToStringXML implements ConverterObjectToString {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterObjectToStringXML.class.getName());

    @Override
    public <T> String getMessage(T value) {
       String message = null;
        try {
            JAXBContext jaxbRootContext = null;
            StringWriter writer = new StringWriter();
            jaxbRootContext = JAXBContext.newInstance(value.getClass());
            Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(value, writer);
            message = writer.toString();
            LOGGER.info(message);
        } catch (JAXBException e) {
            LOGGER.error("Some jaxB exception", e);
        }
        return message;
    }

}
