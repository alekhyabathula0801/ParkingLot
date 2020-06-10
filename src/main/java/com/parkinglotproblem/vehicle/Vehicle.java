package com.parkinglotproblem.vehicle;

import com.parkinglotproblem.exception.ParkingLotException;

public class Vehicle {

    public String vehicleNumber;

    public Vehicle(String vehicleNumber) {
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        this.vehicleNumber = vehicleNumber;
    }

}
