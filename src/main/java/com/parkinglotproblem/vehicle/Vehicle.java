package com.parkinglotproblem.vehicle;

import com.parkinglotproblem.exception.ParkingLotException;

import java.util.Objects;

public class Vehicle {

    public String vehicleNumber;
    long inTime;

    public Vehicle(String vehicleNumber) {
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        this.vehicleNumber = vehicleNumber;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleNumber, vehicle.vehicleNumber);
    }

}
