package com.gitHub.xMIFx.aspects;

import com.gitHub.xMIFx.domain.Worker;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public aspect InstallerWorkerNameToLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstallerWorkerNameToLogger.class.getName());
    private static final String COOKIE_NAME = "worker";


    pointcut controllerMethods(HttpServletRequest req, HttpServletResponse resp):
            ((execution(protected !static void com.gitHub.xMIFx.view.servlets.controllers.DepartmentController.*(..))
                    || execution(protected !static void com.gitHub.xMIFx.view.servlets.controllers.WorkerController.*(..)))
                    && args(req, resp));

    @SuppressAjWarnings({"adviceDidNotMatch"})
    before (HttpServletRequest req, HttpServletResponse resp):controllerMethods(req, resp){
        Worker worker = (Worker) req.getSession().getAttribute(COOKIE_NAME);
        String workerName;
        if (worker == null) {
            workerName = "someone";
        } else {
            workerName = worker.getName();
            worker = null;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("aspect before: " + workerName + " " + thisJoinPoint.getSignature());
        }

        MDC.put(COOKIE_NAME, workerName);
    }
    @SuppressAjWarnings({"adviceDidNotMatch"})
    after (HttpServletRequest req, HttpServletResponse resp):controllerMethods(req, resp){
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("aspect after: " + req.getSession().getAttribute(COOKIE_NAME) + " " + thisJoinPoint.getSignature());
        }
        MDC.remove(COOKIE_NAME);
    }
}
