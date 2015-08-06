package com.gitHub.xMIFx.view.servlets.listeners;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class AuthorizationListener implements HttpSessionListener {
    private static final String COOKIE_NAME = "worker";
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationListener.class.getName());

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Session created");
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        Worker worker = (Worker) httpSessionEvent.getSession().getAttribute(COOKIE_NAME);
        String someText;
        if (worker != null) {
            OnlineWorkerHolder.getOnlineWorkerHolder().remove(worker);
            someText = "with worker: " + worker.getName();
        } else {
            someText = "without worker";
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Session destroyed " + someText);
        }
    }
}
