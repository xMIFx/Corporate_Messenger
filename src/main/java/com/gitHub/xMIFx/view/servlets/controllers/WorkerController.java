package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.services.FinderType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 04.07.2015.
 */
@WebServlet("/worker.do")
public class WorkerController extends HttpServlet {

    private static final String PAGE_OK = "pages/workers.jsp";
    private RecipientOfResponseForWorker recipientOfResponseForWorker = new RecipientOfResponseForWorker();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        if (action != null) {
            String answerStr = null;
            if (exceptionFromFilter != null) {
                answerStr = recipientOfResponseForWorker.getAnswerAboutException(exceptionFromFilter);
            } else if ("getAll".equalsIgnoreCase(action)) {
                answerStr = recipientOfResponseForWorker.getAll();
            } else if (action.startsWith("findByPartOf")) {
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
            } else if ("getByID".equalsIgnoreCase(action)) {
                Long id = Long.valueOf(req.getParameter("id"));
                answerStr = recipientOfResponseForWorker.getByID(id);
            }
            sendAnswer(answerStr, resp);
        } else {
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        String answerStr = null;
        if (exceptionFromFilter != null) {
            answerStr = recipientOfResponseForWorker.getAnswerAboutException(exceptionFromFilter);
        } else {
            Long id = Long.valueOf(req.getParameter("id"));
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String pas = req.getParameter("password");
            int objVersion = Integer.parseInt(req.getParameter("objVersion"));
            String depName = req.getParameter("depName");
            answerStr = recipientOfResponseForWorker.update(id, name, login, pas, objVersion, depName);
        }
        sendAnswer(answerStr, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        String answerStr = null;
        if (exceptionFromFilter != null) {
            answerStr = recipientOfResponseForWorker.getAnswerAboutException(exceptionFromFilter);
        } else {
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String pas = req.getParameter("password");
            answerStr = recipientOfResponseForWorker.create(name, login, pas);
        }
        sendAnswer(answerStr, resp);
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exceptionFromFilter = (String) req.getAttribute("Exception");
        String answerStr = null;
        if (exceptionFromFilter != null) {
            answerStr = recipientOfResponseForWorker.getAnswerAboutException(exceptionFromFilter);
        } else {
            Long id = Long.valueOf(req.getParameter("id"));
            answerStr = recipientOfResponseForWorker.deleteByID(id);
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
