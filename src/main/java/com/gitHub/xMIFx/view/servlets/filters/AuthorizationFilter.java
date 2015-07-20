package com.gitHub.xMIFx.view.servlets.filters;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.servlets.DeterminantOfThePageTo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Vlad on 20.07.2015.
 */
@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private FilterConfig filterConfig;
    private static final String COOKIE_NAME = "user";
    private static final WorkerService workerService = new WorkerServiceImpl();


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
                && (worker.getId() != Long.valueOf(cookieFromClient.getValue()))) {
            cookieFromClient.setMaxAge(0);
            cookieFromClient.setValue(null);
            resp.addCookie(cookieFromClient);
            worker = null;
            req.getSession().setAttribute(COOKIE_NAME, worker);
        }

        // Session destroyed with attributes, so we need to refresh it
        if (worker == null && cookieFromClient != null) {
            worker = workerService.getByID(Long.valueOf(cookieFromClient.getValue()));
            // need to use workers online holder
            req.getSession().setAttribute(COOKIE_NAME, worker);
        }
        if (worker == null) {
            if (!checkNoAdminWorkersForRights(req.getMethod(), req.getQueryString())) {
                req.setAttribute("Exception", "Access denied!");
            } else if (req.getServletPath().contains("messanger.do")) {
                needToRedirect = true;

            } else {/*NOP*/}
        } else if (!worker.isAdmin()) {
            if (!checkNoAdminWorkersForRights(req.getMethod(), req.getQueryString())) {
                req.setAttribute("Exception", "Access denied!");
            } else {/*NOP*/}

        }

        if (needToRedirect) {
            resp.sendRedirect(DeterminantOfThePageTo.getPageTo(req.getHeader("referer")));
        } else
        //  resp.sendRedirect(pageTo); bad variant. No exception message
        {
            filterChain.doFilter(req, resp);
        }


    }

    private boolean checkNoAdminWorkersForRights(String method, String queryString) {
        boolean rightsIsOK = true;
        if (!"GET".equalsIgnoreCase(method)) {
            rightsIsOK = false;
        } else if (queryString != null && !"action=getAll".equalsIgnoreCase(queryString) && !queryString.contains("action=findByPartOf")) {
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