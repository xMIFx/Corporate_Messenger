package com.gitHub.xMIFx.view.servlets.websockets;

import com.gitHub.xMIFx.domain.Department;
import com.gitHub.xMIFx.domain.Worker;
import com.gitHub.xMIFx.repositories.realisationForDTO.DepartmentsHolder;
import com.gitHub.xMIFx.services.implementationServices.ConverterObjectToStringJSON;
import com.gitHub.xMIFx.services.implementationServices.DepartmentServiceImpl;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.ConverterObjectToString;
import com.gitHub.xMIFx.services.interfaces.DepartmentService;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import com.gitHub.xMIFx.view.DTOForView.OnlineWorker;
import com.gitHub.xMIFx.view.domainForView.OnlineWorkerHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 24.07.2015.
 */
class RecipientOfResponseForChat {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipientOfResponseForChat.class.getName());
    private static final DepartmentService departmentService = new DepartmentServiceImpl();
    private static final WorkerService workerService = new WorkerServiceImpl();
    private static final ConverterObjectToString CONVERTER_OBJECT_TO_STRING = new ConverterObjectToStringJSON();

    String getAllDepartmentWithWorkers() {
        String answer = null;
        List<Department> departmentList = departmentService.getAll();
        DepartmentsHolder departmentsHolder = new DepartmentsHolder();
        departmentsHolder.setDepartments(departmentList);
        answer = CONVERTER_OBJECT_TO_STRING.getMessage(departmentsHolder);
        return answer;
    }

    String getAnswerAboutOnlineUser(Worker worker, boolean online) {
        OnlineWorker onlineWorker = new OnlineWorker(online, worker);
        String answer = CONVERTER_OBJECT_TO_STRING.getMessage(onlineWorker);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(answer);
        }
        return answer;
    }

    String getFirstMessage(Long currentWorkerID) {
        String answer = null;

        List<Worker> workerList = workerService.getAll();
        List<Worker> onlineWorkerList = OnlineWorkerHolder.getOnlineWorkerHolder().getOnlineWorkers();
      /* need List with new Messages*/
        Map<String, DepartmentForView> departmentForViewMap = new HashMap<>();

        for (Worker worker : workerList) {
            if (worker.getId() == currentWorkerID) {
                continue;
            }
            OnlineWorker onlineWorker = new OnlineWorker();
            onlineWorker.setWorker(worker);
            if (onlineWorkerList.contains(worker)) {
                onlineWorker.setOnline(true);
            } else {
                onlineWorker.setOnline(false);
            }
            onlineWorker.setCountNewMessages(1);
            DepartmentForView departmentForView;
            if (departmentForViewMap.containsKey(worker.getDepartmentName())) {
                departmentForView = departmentForViewMap.get(worker.getDepartmentName());

            } else {
                departmentForView = new DepartmentForView(new Department(worker.getDepartmentName()));
                departmentForViewMap.put(worker.getDepartmentName(), departmentForView);
            }
            departmentForView.addNewMessages(onlineWorker.getCountNewMessages());
            departmentForView.addWorker(onlineWorker);
        }
        List<DepartmentForView> departments = new ArrayList<>();
        departments.addAll(departmentForViewMap.values());
        answer = CONVERTER_OBJECT_TO_STRING.getMessage(new HolderForDepartmentView(departments));
        LOGGER.info(answer);
        return answer;
    }

    class DepartmentForView {
        private Department department;
        private int countNewMessages;
        private List<OnlineWorker> workers;

        public DepartmentForView(Department department) {
            this.department = department;
            this.workers = new ArrayList<>();
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        public List<OnlineWorker> getWorkers() {
            return workers;
        }

        public void setWorkers(List<OnlineWorker> workers) {
            this.workers = workers;
        }

        public void addWorker(OnlineWorker worker) {
            this.workers.add(worker);
        }

        public int getCountNewMessages() {
            return countNewMessages;
        }

        public void setCountNewMessages(int countNewMessages) {
            this.countNewMessages = countNewMessages;
        }

        public void addNewMessages(int countNewMessages) {
            this.countNewMessages = this.countNewMessages + countNewMessages;
        }
    }


    class HolderForDepartmentView {
        private List<DepartmentForView> departments;

        public HolderForDepartmentView(List<DepartmentForView> departments) {
            this.departments = departments;
        }

        public List<DepartmentForView> getDepartments() {
            return departments;
        }
    }
}
