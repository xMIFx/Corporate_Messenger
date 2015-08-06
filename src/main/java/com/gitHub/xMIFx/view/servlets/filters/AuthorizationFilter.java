package com.gitHub.xMIFx.view.servlets.filters;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder;
import com.gitHub.xMIFx.view.servlets.AjaxWriter;
import com.gitHub.xMIFx.view.servlets.DeterminantOfThePageTo;
import com.gitHub.xMIFx.view.servlets.controllers.RecipientOfResponseForWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(value = "authorizationFilter")
public class AuthorizationFilter extends AjaxWriter implements Filter {
    private FilterConfig filterConfig;
    private static final String COOKIE_NAME = "worker";
    @Autowired
    @Qualifier("recipientOfResponseForWorker")
    private RecipientOfResponseForWorker recipientOfResponseForWorker;

    @Autowired
    @Qualifier("workerService")
    private WorkerService workerService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean needToRedirect = false;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        req.getSession().setMaxInactiveInterval(900);//15 min
        Cookie cookieFromClient = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    cookieFromClient = cookie;
                    break;
                }

            }
        }

        Worker worker = (Worker) req.getSession().getAttribute(COOKIE_NAME);
        // don't match cookie and session attribute
        if (cookieFromClient != null
                && worker != null
                && (!worker.getId().equals(Long.valueOf(cookieFromClient.getValue())))) {
            cookieFromClient.setMaxAge(0);
            cookieFromClient.setValue(null);
            resp.addCookie(cookieFromClient);
            worker = null;
            req.getSession().setAttribute(COOKIE_NAME, worker);
        }

        if (cookieFromClient != null)

            // Session destroyed with attributes, so we need to refresh it
            if (worker == null && cookieFromClient != null) {

                worker = workerService.getByID(Long.valueOf(cookieFromClient.getValue()));
                if (worker == null) {
                    //Clear cookie
                    cookieFromClient.setMaxAge(0);
                    cookieFromClient.setValue(null);
                    resp.addCookie(cookieFromClient);
                } else {
                    OnlineWorkerHolder.getOnlineWorkerHolder().add(worker);
                    req.getSession().setAttribute(COOKIE_NAME, worker);
                }
            }
        String answerStr = null;
        if (worker == null && req.getServletPath().contains("messenger.do")) {
            needToRedirect = true;
        } else if (worker == null || (worker != null && !worker.isAdmin())) {
            if (!req.getServletPath().contains("authorization.do") && !checkNoAdminWorkersForRights(req.getMethod(), req.getQueryString())) {
                answerStr = recipientOfResponseForWorker.getAnswerAboutException("Access denied!");
            }
        }

        if (needToRedirect) {
            resp.sendRedirect(DeterminantOfThePageTo.getPageTo(req.getHeader("referer")));
        } else if (answerStr != null) {
            sendAnswer(answerStr, resp);
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    private boolean checkNoAdminWorkersForRights(String method, String queryString) {
        boolean rightsIsOK = true;
        if (!"GET".equalsIgnoreCase(method) ||
                (queryString != null
                        && !"action=getAll".equalsIgnoreCase(queryString)
                        && !queryString.contains("action=findByPartOf")
                        && !queryString.contains("action=findByPartOf"))) {
            rightsIsOK = false;
        } else {
            rightsIsOK = true;
        }
        return rightsIsOK;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
