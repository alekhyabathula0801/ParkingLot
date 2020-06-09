package com.parkinglotproblem.parkingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingRepository {

    int MAXIMUM_CAPACITY = 100;
    List<Customer> customers = new ArrayList<>(MAXIMUM_CAPACITY);
    Map<String,List<Customer>> parkingDetails = new HashMap<>();

    public boolean addVehicle(String vehicleNumber,Customer customer) {
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        customers.add(customer);
        parkingDetails.put(vehicleNumber, customers);
        return true;
    }

    public int getSize() {
        return parkingDetails.size();
    }

}
