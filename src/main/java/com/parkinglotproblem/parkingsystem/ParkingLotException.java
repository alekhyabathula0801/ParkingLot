package com.parkinglotproblem.parkingsystem;

public class ParkingLotException extends RuntimeException{

    public enum ExceptionType {
        ENTERED_NULL,ENTERED_EMPTY,DATA_EXISTS,DATA_DOESNT_EXISTS,PARKING_LOT_IS_FULL
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
