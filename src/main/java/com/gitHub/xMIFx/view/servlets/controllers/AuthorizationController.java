package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.WorkerService;

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
    private static final String COOKIE_NAME = "user";
    private static final String WRONG_PARAMETERS = "wrong";
    private static final String PAGE_MAIN = "/main.do";
    private static final String PAGE_DEPARTMENT = "/department.do";
    private static final String PAGE_WORKER = "/worker.do";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final WorkerService workerService = new WorkerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String enteredUserName = req.getParameter(LOGIN);
        String enteredPassword = req.getParameter(PASSWORD);
        String pageFrom = req.getHeader("referer");
        String pageTo = getPageTo(pageFrom);
        Worker worker = workerService.getByLoginPassword(enteredUserName, enteredPassword);
        System.out.println(worker);
        if (worker == null) {
            req.setAttribute(WRONG_PARAMETERS, true);
        } else {
            req.setAttribute(WRONG_PARAMETERS, false);
            Cookie userCookie = new Cookie(COOKIE_NAME, worker.getId().toString());
            // need to have Worker online List
            userCookie.setMaxAge(3600);
            resp.addCookie(userCookie);
            req.getSession().setAttribute(COOKIE_NAME, worker);
        }
        req.getRequestDispatcher(pageTo).forward(req, resp);
    }

    private String getPageTo(String pageFrom) {
        String pageTo = PAGE_MAIN;
        if (pageFrom == null) {
            pageTo = PAGE_MAIN;
        } else if (pageFrom.contains(PAGE_MAIN)) {
            pageTo = PAGE_MAIN;
        } else if (pageFrom.contains(PAGE_DEPARTMENT)) {
            pageTo = PAGE_DEPARTMENT;
        } else if (pageFrom.contains(PAGE_WORKER)) {
            pageTo = PAGE_WORKER;
        }
        return pageTo;
    }
}

