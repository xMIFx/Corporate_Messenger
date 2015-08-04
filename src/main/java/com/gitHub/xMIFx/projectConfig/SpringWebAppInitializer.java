package com.gitHub.xMIFx.projectConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Vlad on 04.08.2015.
 */
public class SpringWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {

            final AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
            springContext.register(MvcConfig.class, DataBaseConfig.class);


           /* final WebApplicationContext context = getContext();*/
       /* springContext.register(DataBaseConfig.class);*/
        servletContext.addListener(new ContextLoaderListener(springContext));
        final ServletRegistration.Dynamic springServlet = servletContext.addServlet("myServlet", new DispatcherServlet(springContext));
        springServlet.setLoadOnStartup(1);
        springServlet.addMapping("/");
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.gitHub.xMIFx.projectConfig");
        return context;
    }
}
