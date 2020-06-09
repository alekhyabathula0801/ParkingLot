package com.parkinglotproblem.parkingsystem;

public class Customer {

    String customerName;
    VehicleType vehicleType;

    public Customer(String customerName, VehicleType vehicleType) {
        if(customerName == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(customerName.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        this.customerName = customerName;
        this.vehicleType = vehicleType;
    }

}
