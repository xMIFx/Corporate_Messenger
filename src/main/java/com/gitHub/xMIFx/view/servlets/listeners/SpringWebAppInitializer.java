package com.gitHub.xMIFx.view.servlets.listeners;

import com.gitHub.xMIFx.projectConfig.springConfigs.MainSpringConfig;
import com.gitHub.xMIFx.projectConfig.springConfigs.MvcConfig;
import com.gitHub.xMIFx.view.servlets.filters.AuthorizationFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by Vlad on 04.08.2015.
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
/*implements WebApplicationInitializer*/  {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{MainSpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy  springSecurityFilterChain =    new DelegatingFilterProxy();
        springSecurityFilterChain.setTargetBeanName("authorizationFilter");
        return new Filter[] {springSecurityFilterChain};
    }

   /* @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {

        final AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(DataBaseConfig.class);
        servletContext.addListener(new ContextLoaderListener(springContext));

           *//* final WebApplicationContext context = getContext();*//*
       *//* springContext.register(DataBaseConfig.class);*//*

       *//* final ServletRegistration.Dynamic springServlet = servletContext.addServlet("myServlet", new DispatcherServlet(springContext));
        springServlet.setLoadOnStartup(1);*//*
      *//*  springServlet.addMapping("/");*//*
    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.gitHub.xMIFx.projectConfig");
        return context;
    }*/
}
