package com.gitHub.xMIFx.view.servlets.controllers;

import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.AbstractFactoryForDAO;
import com.gitHub.xMIFx.repositories.abstractFactoryDAO.CreatorDAOFactory;
import com.gitHub.xMIFx.repositories.dto.WorkersHolder;
import com.gitHub.xMIFx.repositories.interfacesDAO.WorkerDAO;
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
    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class.getName());
    private static AbstractFactoryForDAO abstractFactoryForDAOf = CreatorDAOFactory.getAbstractFactoryForDAO();
    private static WorkerDAO workerDAO = abstractFactoryForDAOf.getWorkersDAOImpl();

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
        boolean itsDeleting = false;
        try {
            if (action.equalsIgnoreCase("getAll")) {
                answerStr = getAllWorkers();
            } else {

                Long id = null;
                String name = req.getParameter("name");
                String login = req.getParameter("login");
                String pas = req.getParameter("password");
                Worker worker = new Worker(id, name, login, pas);
                if (action.equalsIgnoreCase("create")) {
                    workerDAO.save(worker);

                } else if (action.equalsIgnoreCase("update")) {
                    int objVersion = Integer.valueOf(req.getParameter("objVersion"));
                    id = Long.valueOf(req.getParameter("id"));
                    worker.setId(id);
                    worker.setDepartmentName(req.getParameter("depName"));
                    workerDAO.update(worker);
                }
                else if (action.equalsIgnoreCase("getByID")){
                    id = Long.valueOf(req.getParameter("id"));
                    worker = workerDAO.getById(id);
                } else if (action.equalsIgnoreCase("deleteByID")) {
                    id = Long.valueOf(req.getParameter("id"));
                    worker = workerDAO.getById(id);
                    itsDeleting = true;
                    boolean itsOk = workerDAO.remove(worker);
                    if (!itsOk) {
                        worker = null;
                    }
                }

                answerStr = getWorker(worker);

            }
        } catch (JAXBException e) {
            logger.error("some jaxB exception: ", e);
        }
        if (answerStr == null) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setContentType("text/xml");
            resp.setHeader("Cache-Control", "no-cache");
            resp.getWriter().write(answerStr);
        }
    }

    private String getWorker(Worker worker) throws JAXBException {
        JAXBContext jaxbRootContext = null;
        StringWriter writer = new StringWriter();
        jaxbRootContext = JAXBContext.newInstance(Worker.class);
        Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(worker, writer);
        System.out.println(writer.toString());
        return writer.toString();
    }

    private String getLong(Long workerID) throws JAXBException {
        JAXBContext jaxbRootContext = null;
        StringWriter writer = new StringWriter();
        jaxbRootContext = JAXBContext.newInstance(Long.class);
        Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(workerID, writer);
        System.out.println(writer.toString());
        return writer.toString();
    }

    private String getAllWorkers() throws JAXBException {
        List<Worker> workerList = workerDAO.getAll();
        WorkersHolder workersHolder = new WorkersHolder();
        workersHolder.setWorkers(workerList);
        JAXBContext jaxbRootContext = null;
        StringWriter writer = new StringWriter();
        jaxbRootContext = JAXBContext.newInstance(WorkersHolder.class);
        Marshaller jaxbMarshaller = jaxbRootContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(workersHolder, writer);
        return writer.toString();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
