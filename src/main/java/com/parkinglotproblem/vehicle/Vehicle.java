package com.parkinglotproblem.vehicle;

import com.parkinglotproblem.exception.ParkingLotException;

import java.util.Objects;

public class Vehicle {

    public enum VehicleSize{
        SMALL(1),LARGE(2);
        public final int size;
        VehicleSize(int size) {
            this.size = size;
        }
    }
    public String vehicleNumber;
    public VehicleSize vehicleSize;

    public Vehicle(String vehicleNumber,VehicleSize vehicleSize) {
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        this.vehicleNumber = vehicleNumber;
        this.vehicleSize = vehicleSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleNumber, vehicle.vehicleNumber) &&
                vehicleSize == vehicle.vehicleSize;
    }

}
