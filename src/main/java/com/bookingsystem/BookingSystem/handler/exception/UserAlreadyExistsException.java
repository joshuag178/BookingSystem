package com.bookingsystem.BookingSystem.handler.exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
