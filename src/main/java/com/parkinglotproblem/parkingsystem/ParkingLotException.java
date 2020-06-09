package com.parkinglotproblem.parkingsystem;

public class ParkingLotException extends RuntimeException{

    enum ExceptionType {
        ENTERED_NULL,ENTERED_EMPTY,DATA_EXISTS,DATA_DOESNT_EXISTS
    }

    ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}