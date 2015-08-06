package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.view.servlets.AjaxWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WorkerController extends AjaxWriter {
   /* private static final String PAGE_OK = "pages/workers.jsp";*/

    @Autowired
    @Qualifier("recipientOfResponseForWorker")
    private RecipientOfResponseForWorker recipientOfResponseForWorker;

    @RequestMapping(value = "worker.do", method = RequestMethod.GET)
    protected String doGet() throws ServletException, IOException {
        return "workers";
    }

    @RequestMapping(value = "worker.do", params = {"action=getAll"}, method = RequestMethod.GET)
    protected void doGetAll(HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = recipientOfResponseForWorker.getAll();
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "worker.do", params = {"action=getByID", "id"}, method = RequestMethod.GET)
    protected void doGetByID(Long id, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        String answerStr = recipientOfResponseForWorker.getByID(id);
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "worker.do", params = {"action", "valueForSearch", "searchType"}, method = RequestMethod.GET)
    protected void doFind(String valueForSearch, String searchType, HttpServletResponse resp) throws ServletException, IOException {
        FinderType finderType = null;
        if ("name".equals(searchType)) {
            finderType = FinderType.NAME;
        } else if ("login".equals(searchType)) {
            finderType = FinderType.LOGIN;
        } else if ("departmentName".equals(searchType)) {
            finderType = FinderType.DEPARTMENT;
        }
        String answerStr = recipientOfResponseForWorker.find(finderType, valueForSearch);
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "worker.do", method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String pas = req.getParameter("password");
        int objVersion = Integer.parseInt(req.getParameter("objVersion"));
        String depName = req.getParameter("depName");
        String answerStr = recipientOfResponseForWorker.update(id, name, login, pas, objVersion, depName);
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "worker.do", method = RequestMethod.PUT)
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String pas = req.getParameter("password");
        String answerStr = recipientOfResponseForWorker.create(name, login, pas);
        sendAnswer(answerStr, resp);
    }


    @RequestMapping(value = "worker.do", method = RequestMethod.DELETE)
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String answerStr = recipientOfResponseForWorker.deleteByID(id);
        sendAnswer(answerStr, resp);

    }
}
