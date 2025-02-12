package com.example.URL.Shortener.Service.exception;

public class UrlNotFoundException extends RuntimeException{
    public UrlNotFoundException(String message){
        super(message);
    }
}
