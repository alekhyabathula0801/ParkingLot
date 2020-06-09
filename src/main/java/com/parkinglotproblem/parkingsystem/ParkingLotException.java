package com.parkinglotproblem.parkingsystem;

public class ParkingLotException extends RuntimeException{

    enum ExceptionType {
        ENTERED_NULL,ENTERED_EMPTY
    }

    ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
