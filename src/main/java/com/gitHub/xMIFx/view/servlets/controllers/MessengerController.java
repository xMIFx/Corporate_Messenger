package com.gitHub.xMIFx.view.servlets.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class MessengerController {
    private static final String PAGE_OK = "WEB-INF/messenger.jsp";

    @RequestMapping(value = "messenger.do")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
