package com.gitHub.xMIFx.aspects;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.view.servlets.websockets.MessengerWebSocket;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public aspect AuthorizationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationAspect.class.getName());

    pointcut setterOnlineWorker(Worker worker):
            (call(!static void com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder.add(..))
                    && args(worker));
    pointcut removerOnlineWorker(Worker worker):
            (call(!static void com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder.remove(..))
                    && args(worker));

    @SuppressAjWarnings({"adviceDidNotMatch"})
    after (Worker worker): setterOnlineWorker(worker){
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("aspect method add: " + worker);
        }
        MessengerWebSocket.createSendMessageAboutOnline(worker, true);
    }

    @SuppressAjWarnings({"adviceDidNotMatch"})
    after (Worker worker): removerOnlineWorker(worker){
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("aspect method remove: " + worker);
        }
        MessengerWebSocket.createSendMessageAboutOnline(worker, false);
    }

    /*for test, that it works*/
    pointcut setterWorker(Worker worker): target(worker)
            && set(!static String com.gitHub.xMIFx.domain.Worker.name);

    @SuppressAjWarnings({"adviceDidNotMatch"})
    after (Worker worker): setterWorker(worker){

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("aspect name: " + worker);
        }
    }


}
