package com.gitHub.xMIFx.projectConfig.springConfigs;


import com.gitHub.xMIFx.services.implementationServices.ChatServiceImpl;
import com.gitHub.xMIFx.services.implementationServices.DepartmentServiceImpl;
import com.gitHub.xMIFx.services.implementationServices.WorkerServiceImpl;
import com.gitHub.xMIFx.services.interfaces.ChatService;
import com.gitHub.xMIFx.services.interfaces.DepartmentService;
import com.gitHub.xMIFx.services.interfaces.WorkerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ServiceConfig {

    @Bean(name = "workerService")
    public WorkerService workerService(){
        return new WorkerServiceImpl();
    }

    @Bean(name = "departmentService")
    public DepartmentService departmentService(){
        return new DepartmentServiceImpl();
    }

    @Bean(name = "chatService")
    public ChatService chatService(){
        return new ChatServiceImpl();
    }
}
