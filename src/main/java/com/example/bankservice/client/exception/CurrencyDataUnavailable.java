package com.example.bankservice.client.exception;

public class CurrencyDataUnavailable extends RuntimeException {

    public CurrencyDataUnavailable(){

    }
    public CurrencyDataUnavailable(String s) {
        super(s);
    }
}
