package com.arturfrimu.jwt.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Slf4j
@Component
public class HttpServletRequestLogger {

    public void logRequestDetails(HttpServletRequest request) {
        log.info("\nRequest Details:\n");

        // Log basic request properties
        log.info("Context Path: {}", request.getContextPath());
        log.info("Request URL: {}", request.getRequestURL());
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Servlet Path: {}", request.getServletPath());
        log.info("Method: {}", request.getMethod());
        log.info("Protocol: {}", request.getProtocol());
        log.info("Path Info: {}", request.getPathInfo());
        log.info("Remote Address: {}", request.getRemoteAddr());

        // Log headers
        log.info("\nHeaders:\n");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            log.info("{}: {}", header, request.getHeader(header));
        }

        // Log parameters
        log.info("\nParameters:\n");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            log.info("{}: {}", param, request.getParameter(param));
        }

        // Log session info if exists
        if (request.getSession(false) != null) {
            log.info("Session ID: {}", request.getSession().getId());
        } else {
            log.info("No Session found.");
        }
    }
}
