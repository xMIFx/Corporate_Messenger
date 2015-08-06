package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.view.servlets.AjaxWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DepartmentController extends AjaxWriter {
    /*private static final String PAGE_OK = "pages/departments.jsp";*/
    @Autowired
    @Qualifier("recipientOfResponseForDepartment")
    private RecipientOfResponseForDepartment recipientOfResponseForDepartment;

    @Autowired
    @Qualifier("recipientOfResponseForWorker")
    private RecipientOfResponseForWorker recipientOfResponseForWorker;

    @RequestMapping(value = "department.do", method = RequestMethod.GET)
    protected String doGet() throws ServletException, IOException {
        return "departments";
    }

    @RequestMapping(value = "department.do", params = {"action=getAll"}, method = RequestMethod.GET)
    protected void doGetAll(HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = recipientOfResponseForDepartment.getAll();
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", params = {"action", "valueForSearch", "searchType"}, method = RequestMethod.GET)
    protected void doFind(String action, String valueForSearch, String searchType, HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = null;
        FinderType finderType = null;
        if (action.startsWith("findByPartOf")) {
            if ("name".equals(searchType)) {
                finderType = FinderType.NAME;
            }
            answerStr = recipientOfResponseForDepartment.find(finderType, valueForSearch);
        } else if (action.startsWith("findWorker")) {
            if ("name".equals(searchType)) {
                finderType = FinderType.NAME;
            } else if ("login".equals(searchType)) {
                finderType = FinderType.LOGIN;
            } else if ("departmentName".equals(searchType)) {
                finderType = FinderType.DEPARTMENT;
            }
            answerStr = recipientOfResponseForWorker.find(finderType, valueForSearch);
        }
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", params = {"action=getAllWorkers"}, method = RequestMethod.GET)
    protected void doGetAllWorkers(HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = recipientOfResponseForWorker.getAll();
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", params = {"action=getByID", "id"}, method = RequestMethod.GET)
    protected void doGetById(Long id, HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = recipientOfResponseForDepartment.getByID(id);
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", params = "department", method = RequestMethod.POST)
    protected void doPost(String department, HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = recipientOfResponseForDepartment.update(department);
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", params = "department", method = RequestMethod.PUT)
    protected void doPut(String department, HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = recipientOfResponseForDepartment.create(department);
        sendAnswer(answerStr, resp);
    }

    @RequestMapping(value = "department.do", params = "id", method = RequestMethod.DELETE)
    protected void doDelete(Long id, HttpServletResponse resp) throws ServletException, IOException {
        String answerStr = recipientOfResponseForDepartment.deleteByID(id);
        sendAnswer(answerStr, resp);
    }
}
