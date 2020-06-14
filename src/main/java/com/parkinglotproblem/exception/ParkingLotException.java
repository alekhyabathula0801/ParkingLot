package com.parkinglotproblem.exception;

public class ParkingLotException extends RuntimeException{

    public enum ExceptionType {
        ENTERED_NULL,ENTERED_EMPTY, VEHICLE_EXISTS, NO_VEHICLE,PARKING_LOT_IS_FULL,SLOT_OCCUPIED,PARKING_LOT_SIZE_IS_LOW
    }

    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
