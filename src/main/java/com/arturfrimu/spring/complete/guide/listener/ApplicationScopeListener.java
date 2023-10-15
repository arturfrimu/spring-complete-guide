package com.arturfrimu.spring.complete.guide.listener;

import com.arturfrimu.spring.complete.guide.service.scope.ApplicationService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationScopeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationService applicationService = new ApplicationService();
        servletContextEvent.getServletContext().setAttribute("applicationService", applicationService);
    }
}
