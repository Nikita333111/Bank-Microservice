package com.example.bankservice.exception;

public class LimitAlreadyExist extends RuntimeException {

    public LimitAlreadyExist(){

    }
    public LimitAlreadyExist(String s) {
        super(s);
    }
}
