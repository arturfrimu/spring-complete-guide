package com.arturfrimu.crud.exception;

public class DeveloperWithEmailAlreadyExistsException extends ApiException {
    public DeveloperWithEmailAlreadyExistsException(String message, String errorCode) {
        super(message, errorCode);
    }
}
