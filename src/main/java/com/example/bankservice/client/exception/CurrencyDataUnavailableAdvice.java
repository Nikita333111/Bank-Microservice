package com.example.bankservice.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CurrencyDataUnavailableAdvice {
    @ExceptionHandler(CurrencyDataUnavailable.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleCurrencyDataUnavailableException(CurrencyDataUnavailable exception){
        return exception.getMessage();
    }
}
