package com.Bug_Tracker.exception.domain;

import com.Bug_Tracker.exception.ExceptionHandling;

public class UsernameExistException extends Exception {
    public UsernameExistException(String message) {
        super(message);
    }
}
