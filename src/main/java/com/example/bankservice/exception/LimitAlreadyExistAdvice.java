package com.example.bankservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LimitAlreadyExistAdvice {
    @ExceptionHandler(LimitAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleLimitAlreadyExistException(LimitAlreadyExist exception) {
        return exception.getMessage();
    }
}
