package com.parkinglotproblem.parkingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingRepository {

    int MAXIMUM_CAPACITY = 100;
    List<Customer> customers = new ArrayList<>();
    Map<String,List<Customer>> parkingDetails = new HashMap<>(MAXIMUM_CAPACITY);

    public boolean parkVehicle(String vehicleNumber, Customer customer) {
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        if(parkingDetails.get(vehicleNumber) != null)
            throw new ParkingLotException("Data Exists", ParkingLotException.ExceptionType.DATA_EXISTS);
        customers = new ArrayList<>();
        customers.add(customer);
        parkingDetails.put(vehicleNumber, customers);
        return true;
    }

    public int getSize() {
        return parkingDetails.size();
    }

    public boolean unparkVehicle(String vehicleNumber) {
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        if(parkingDetails.get(vehicleNumber) == null)
            throw new ParkingLotException("Data Doesn't Exists", ParkingLotException.ExceptionType.DATA_DOESNT_EXISTS);
        parkingDetails.remove(vehicleNumber);
        return true;
    }

}
