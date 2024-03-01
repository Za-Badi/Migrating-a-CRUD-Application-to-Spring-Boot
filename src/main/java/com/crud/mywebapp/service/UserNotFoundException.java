package com.crud.mywebapp.service;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String message){
        super(message);
    }
}
