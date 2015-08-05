package com.gitHub.xMIFx.view.servlets.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 01.07.2015.
 */
@Controller
/*@WebServlet("/main.do")*/
public class MainController  /*extends HttpServlet*/{
    private static final String PAGE_OK = "pages/main.jsp";

  /* @Override*/
  @RequestMapping(value = "main.do")
    protected String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      return "main";
    }
}

