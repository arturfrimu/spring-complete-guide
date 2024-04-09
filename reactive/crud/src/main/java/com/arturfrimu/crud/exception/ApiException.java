package com.arturfrimu.crud.exception;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PROTECTED;

@FieldDefaults(level = PROTECTED, makeFinal = true)
public class ApiException extends RuntimeException {

    @Getter
    String errorCode;

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
