package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder;
import com.gitHub.xMIFx.view.servlets.DeterminantOfThePageTo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 19.07.2015.
 */
@WebServlet("/authorization.do")
public class AuthorizationController extends HttpServlet {
    private static final String COOKIE_NAME = "worker";
    private static final String WRONG_PARAMETERS = "wrong";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final WorkerService workerService = new WorkerServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String enteredUserName = req.getParameter(LOGIN);
        String enteredPassword = req.getParameter(PASSWORD);
        String pageFrom = req.getHeader("referer");
        String pageTo = DeterminantOfThePageTo.getPageTo(pageFrom);
        Worker worker = workerService.getByLoginPassword(enteredUserName, enteredPassword);
        if (worker == null) {
            req.setAttribute(WRONG_PARAMETERS, true);
            req.getRequestDispatcher(pageTo).forward(req, resp);
        } else {
            req.setAttribute(WRONG_PARAMETERS, false);
            Cookie userCookie = new Cookie(COOKIE_NAME, worker.getId().toString());
            // need to have List Online Workers
            OnlineWorkerHolder.getOnlineWorkerHolder().add(worker);
            userCookie.setMaxAge(3600);
            resp.addCookie(userCookie);
            req.getSession().setAttribute(COOKIE_NAME, worker);
            resp.sendRedirect(pageTo);
        }

    }


}

