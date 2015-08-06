package com.gitHub.xMIFx.view.servlets.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 01.07.2015.
 */
@Controller

public class MainController {

  @RequestMapping(value = "main.do")
    protected String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      return "main";
    }
}

