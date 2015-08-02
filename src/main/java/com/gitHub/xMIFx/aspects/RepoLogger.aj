package com.gitHub.xMIFx.aspects;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Vlad on 25.07.2015.
 */
public aspect RepoLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.gitHub.xMIFx.repositories.RepoLoggerAspect");

    pointcut repositoriesMethods():
            (execution(public !static * com.gitHub.xMIFx.repositories.interfacesForDAO.DepartmentDAO.*(..))
            ||execution(public !static * com.gitHub.xMIFx.repositories.interfacesForDAO.WorkerDAO.*(..))
                    ||execution(public !static * com.gitHub.xMIFx.repositories.interfacesForDAO.ChatDAO.*(..)));

    @SuppressAjWarnings({"adviceDidNotMatch"})
    before (): repositoriesMethods(){
        LOGGER.info("before in repo: " + thisJoinPoint.getSignature());
    }

    @SuppressAjWarnings({"adviceDidNotMatch"})
    after (): repositoriesMethods(){
        LOGGER.info("after in repo: " + thisJoinPoint.getSignature());
        LOGGER.info("after in repo: " + Arrays.toString(thisJoinPoint.getArgs()));
    }
}
