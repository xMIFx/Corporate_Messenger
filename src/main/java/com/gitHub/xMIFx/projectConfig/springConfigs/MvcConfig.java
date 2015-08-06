package com.gitHub.xMIFx.projectConfig.springConfigs;

import com.gitHub.xMIFx.view.servlets.controllers.RecipientOfResponseForDepartment;
import com.gitHub.xMIFx.view.servlets.controllers.RecipientOfResponseForWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.gitHub.xMIFx.view.servlets" })
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/web/**")
                .addResourceLocations("/web/cssFiles/")
                .addResourceLocations("/web/fonts/")
                .addResourceLocations("/web/images/")
                .addResourceLocations("/web/jsFiles/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/pages/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Bean(name = "recipientOfResponseForWorker")
    public RecipientOfResponseForWorker recipientOfResponseForWorker(){
        return new RecipientOfResponseForWorker();
    }

    @Bean(name = "recipientOfResponseForDepartment")
    public RecipientOfResponseForDepartment recipientOfResponseForDepartment(){
        return new RecipientOfResponseForDepartment();
    }
}
