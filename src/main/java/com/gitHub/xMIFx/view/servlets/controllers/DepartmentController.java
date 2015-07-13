package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.implementationServices.DepartmentServiceImpl;
import com.gitHub.xMIFx.services.interfaces.DepartmentService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vlad on 11.07.2015.
 */
@WebServlet("/department.do")
public class DepartmentController extends HttpServlet {
    private static final String PAGE_OK = "pages/departments.jsp";
    private static final DepartmentService departmentService = new DepartmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println(action);
        if (action != null) {
            readAjax(action, req, resp);
        } else {
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
        }
    }

    private void readAjax(String action, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String answerStr = null;
        if (action.equalsIgnoreCase("getAll")) {
            answerStr = departmentService.getAll();
        } else if (action.startsWith("findByPartOf")) {
            String value = req.getParameter("valueForSearch");
            String searchTypeString = req.getParameter("searchType");
            FinderType finderType = null;
            if ("name".equals(searchTypeString)) {
                finderType = FinderType.NAME;
            }
            answerStr = departmentService.find(finderType, value);
        } else if (action.equalsIgnoreCase("create")) {
            String jsonText = req.getParameter("department");
            answerStr = departmentService.create(jsonText);

        } else if (action.equalsIgnoreCase("update")) {
           String jsonText = req.getParameter("department");
           answerStr = departmentService.update(jsonText);

        } else if (action.equalsIgnoreCase("getByID")) {
            Long id = Long.valueOf(req.getParameter("id"));
            answerStr = departmentService.getByID(id);

        } else if (action.equalsIgnoreCase("deleteByID")) {
            Long id = Long.valueOf(req.getParameter("id"));
            answerStr = departmentService.deleteByID(id);
        }


        if (answerStr == null)

        {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else

        {
            resp.setContentType("text/xml");
            resp.setHeader("Cache-Control", "no-cache");
            resp.getWriter().write(answerStr);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
