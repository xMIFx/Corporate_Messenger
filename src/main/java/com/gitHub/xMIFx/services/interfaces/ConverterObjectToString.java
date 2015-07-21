package com.gitHub.xMIFx.services.interfaces;

import com.gitHub.xMIFx.services.ObjectConvertingType;


/**
 * Created by Vlad on 17.07.2015.
 */
public interface ConverterObjectToString {
    <T> String getMessage(T value, ObjectConvertingType convertingType);

}
