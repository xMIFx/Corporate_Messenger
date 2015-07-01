package com.gitHub.xMIFx.view.servlets.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 01.07.2015.
 */
@WebServlet("/main.do")
public class MainController  extends HttpServlet{
    private static final String PAGE_OK = "pages/main.jsp";
    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}

