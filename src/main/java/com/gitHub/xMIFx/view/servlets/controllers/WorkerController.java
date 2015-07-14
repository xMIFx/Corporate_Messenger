package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.dto.WorkersHolder;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
import com.gitHub.xMIFx.services.FinderType;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.domainForView.ExceptionForView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by Vlad on 04.07.2015.
 */
@WebServlet("/worker.do")
public class WorkerController extends HttpServlet {
    private static final String PAGE_OK = "pages/workers.jsp";
    private static final WorkerService workerService = new WorkerServiceImpl();

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
            answerStr = workerService.getAll();
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
            answerStr = workerService.find(finderType, value);
        } else {

            Long id = Long.valueOf(req.getParameter("id"));
            String name = req.getParameter("name");
            String login = req.getParameter("login");
            String pas = req.getParameter("password");
            if (action.equalsIgnoreCase("create")) {
                answerStr = workerService.create(name, login, pas);

            } else if (action.equalsIgnoreCase("update")) {
                int objVersion = Integer.parseInt(req.getParameter("objVersion"));
                String depName = req.getParameter("depName");
                answerStr = workerService.update(id, name, login, pas, objVersion, depName);

            } else if (action.equalsIgnoreCase("getByID")) {
                answerStr = workerService.getByID(id);

            } else if (action.equalsIgnoreCase("deleteByID")) {
                answerStr = workerService.deleteByID(id);
            }

        }

        if (answerStr == null) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setContentType("text/xml");
            resp.setHeader("Cache-Control", "no-cache");
            resp.getWriter().write(answerStr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
