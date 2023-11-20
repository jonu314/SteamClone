package com.Bug_Tracker.exception.domain;

import com.Bug_Tracker.exception.ExceptionHandling;

public class EmailExistException extends Exception {
    public EmailExistException(String message){
        super(message);
    }
}
