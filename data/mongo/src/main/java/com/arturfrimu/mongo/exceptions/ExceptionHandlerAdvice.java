package com.arturfrimu.mongo.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    void handleException(Exception exception) {
        System.out.println(exception.getMessage());
    }
}
