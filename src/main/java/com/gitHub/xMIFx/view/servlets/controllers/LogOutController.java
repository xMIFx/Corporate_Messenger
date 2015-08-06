package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder;
import com.gitHub.xMIFx.view.servlets.DeterminantOfThePageTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 20.07.2015.
 */

@Controller
public class LogOutController {
    private static final String COOKIE_USER = "worker";

    @RequestMapping(value = "exit.do")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie userCookie = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(COOKIE_USER)) {
                userCookie = cookie;
                break;
            }
        }
        if (userCookie != null) {
            userCookie.setValue(null);
            userCookie.setMaxAge(0);
            resp.addCookie(userCookie);
        }
        Worker worker = (Worker) req.getSession().getAttribute(COOKIE_USER);
        if (worker != null) {
            OnlineWorkerHolder.getOnlineWorkerHolder().remove(worker);
            req.getSession().setAttribute(COOKIE_USER, null);
        }
        String pageFrom = req.getHeader("referer");
        String pageTo = DeterminantOfThePageTo.getPageTo(pageFrom);
        resp.sendRedirect(pageTo);

    }
}

