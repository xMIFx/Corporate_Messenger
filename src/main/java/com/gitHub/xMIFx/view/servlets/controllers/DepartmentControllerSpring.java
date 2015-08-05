package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.services.FinderType;
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
 * Created by Vlad on 11.07.2015.
 */
@Controller
public class DepartmentControllerSpring {
    private static final String PAGE_OK = "pages/departments.jsp";
    private RecipientOfResponseForDepartment recipientOfResponseForDepartment = new RecipientOfResponseForDepartment();
    private RecipientOfResponseForWorker recipientOfResponseForWorker = new RecipientOfResponseForWorker();


    @RequestMapping(value = "department.do", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            readAjax(action, req, resp);
        } else {
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
        }
    }

    private void readAjax(String action, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        String answerStr = null;
        if (exceptionFromFilter != null) {
            answerStr = recipientOfResponseForDepartment.getAnswerAboutException(exceptionFromFilter);
        } else if ("getAll".equalsIgnoreCase(action)) {
             answerStr = recipientOfResponseForDepartment.getAll();
        } else if (action != null && action.startsWith("findByPartOf")) {
            String value = req.getParameter("valueForSearch");
            String searchTypeString = req.getParameter("searchType");
            FinderType finderType = null;
            if ("name".equals(searchTypeString)) {
                finderType = FinderType.NAME;
            }
            answerStr = recipientOfResponseForDepartment.find(finderType, value);
        } else if ("getByID".equalsIgnoreCase(action)) {
            Long id = Long.valueOf(req.getParameter("id"));
            answerStr = recipientOfResponseForDepartment.getByID(id);
        } else if ("getAllWorkers".equalsIgnoreCase(action)) {
            answerStr = recipientOfResponseForWorker.getAll();
        } else if (action != null && action.startsWith("findWorker")) {
            String value = req.getParameter("valueForSearch");
            String searchTypeString = req.getParameter("searchType");
            FinderType finderType = null;
            if ("name".equals(searchTypeString)) {
                finderType = FinderType.NAME;
            } else if ("login".equals(searchTypeString)) {
                finderType = FinderType.LOGIN;
            } else if ("departmentName".equals(searchTypeString)) {
                finderType = FinderType.DEPARTMENT;
            }
            answerStr = recipientOfResponseForWorker.find(finderType, value);
        }
        sendAnswer(answerStr, resp);

    }

    @RequestMapping(value = "department.do", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        String answerStr = null;
        if (exceptionFromFilter != null) {
            answerStr = recipientOfResponseForDepartment.getAnswerAboutException(exceptionFromFilter);
        } else {
            String jsonText = req.getParameter("department");
            answerStr = recipientOfResponseForDepartment.update(jsonText);
        }
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", method = RequestMethod.PUT)
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        String answerStr = null;
        if (exceptionFromFilter != null) {
            answerStr = recipientOfResponseForDepartment.getAnswerAboutException(exceptionFromFilter);
        } else {
            String jsonText = req.getParameter("department");
            answerStr = recipientOfResponseForDepartment.create(jsonText);
        }
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", method = RequestMethod.DELETE)
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        String answerStr = null;
        if (exceptionFromFilter != null) {
            answerStr = recipientOfResponseForDepartment.getAnswerAboutException(exceptionFromFilter);
        } else {
            Long id = Long.valueOf(req.getParameter("id"));
            answerStr = recipientOfResponseForDepartment.deleteByID(id);
        }
        sendAnswer(answerStr, resp);
    }

    private void sendAnswer(String answerStr, HttpServletResponse resp) throws IOException {
        if (answerStr == null) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setContentType("text/xml");
            resp.setHeader("Cache-Control", "no-cache");
            resp.getWriter().write(answerStr);
        }
    }

}
