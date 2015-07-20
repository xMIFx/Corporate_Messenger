package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.services.interfaces.MainService;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Created by Vlad on 17.07.2015.
 */
public abstract class MainServiceImpl implements MainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainServiceImpl.class.getName());
    @Override
    public String getAnswerAboutException(String exceptionMessage) {
        String answer = null;
        ExceptionForView exceptionForView = new ExceptionForView();
        exceptionForView.setExceptionMessage(exceptionMessage);
        try {
            answer = getXMLMessage(exceptionForView);
        } catch (JAXBException e) {
            LOGGER.error("some jaxB exception: ",e);
        }
        return answer;
    }

    @Override
    public <T> String getXMLMessage(T value) throws JAXBException {
        JAXBContext jaxbRootContext = null;
        StringWriter writer = new StringWriter();
        jaxbRootContext = JAXBContext.newInstance(value.getClass());
        Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(value, writer);
        LOGGER.info(writer.toString());
        return writer.toString();
    }
}
