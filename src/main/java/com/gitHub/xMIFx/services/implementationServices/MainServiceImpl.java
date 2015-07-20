package com.gitHub.xMIFx.services.implementationServices;

import com.gitHub.xMIFx.services.interfaces.MainService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Created by Vlad on 17.07.2015.
 */
public abstract class MainServiceImpl implements MainService {
    @Override
    public <T> String getXMLMessage(T value) throws JAXBException {
        JAXBContext jaxbRootContext = null;
        StringWriter writer = new StringWriter();
        jaxbRootContext = JAXBContext.newInstance(value.getClass());
        Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(value, writer);
        System.out.println(writer.toString());
        return writer.toString();
    }
}