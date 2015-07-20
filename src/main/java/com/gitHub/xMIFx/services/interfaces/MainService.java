package com.gitHub.xMIFx.services.interfaces;

import javax.xml.bind.JAXBException;

/**
 * Created by Vlad on 17.07.2015.
 */
public interface MainService {
    <T> String getXMLMessage(T value) throws JAXBException;

    String getAnswerAboutException(String exceptionMessage);
}
