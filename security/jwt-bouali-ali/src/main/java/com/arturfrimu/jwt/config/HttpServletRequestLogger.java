package com.arturfrimu.jwt.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Component
public class HttpServletRequestLogger {

    public void logRequestDetails(HttpServletRequest request) {
        System.out.println("\nRequest Details:\n");

        // Log basic request properties
        System.out.println("Context Path: " + request.getContextPath());
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Servlet Path: " + request.getServletPath());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Protocol: " + request.getProtocol());
        System.out.println("Path Info: " + request.getPathInfo());
        System.out.println("Remote Address: " + request.getRemoteAddr());

        // Log headers
        System.out.println("\nHeaders:\n");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            System.out.println(header + ": " + request.getHeader(header));
        }

        // Log parameters
        System.out.println("\nParameters:\n");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            System.out.println(param + ": " + request.getParameter(param));
        }

        // Log session info if exists
        if (request.getSession(false) != null) {
            System.out.println("Session ID: " + request.getSession().getId());
        } else {
            System.out.println("No Session found.");
        }
    }
}
