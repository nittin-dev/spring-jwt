package com.crud.Exception;

import org.apache.catalina.User;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message){
        super(message);
    }
}
