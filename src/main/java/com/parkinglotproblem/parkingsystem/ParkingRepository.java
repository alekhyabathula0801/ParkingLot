package com.parkinglotproblem.parkingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingRepository {

    static int MAXIMUM_CAPACITY = 100;
    Map<String,Customer> parkingDetails = new HashMap<>();

    static List<Integer> availableSlots = new ArrayList<>();

    static {
        for (int slot=1; slot<=MAXIMUM_CAPACITY; slot++)
            availableSlots.add(slot);
    }

    public boolean parkVehicle(String vehicleNumber, Customer customer) {
        isFull();
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        if(parkingDetails.get(vehicleNumber) != null)
            throw new ParkingLotException("Data Exists", ParkingLotException.ExceptionType.DATA_EXISTS);
        parkingDetails.put(vehicleNumber, new Customer(customer,availableSlots.get(0)));
        availableSlots.remove(0);
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
        Customer customer = parkingDetails.get(vehicleNumber);
        availableSlots.add(customer.slotNumber);
        parkingDetails.remove(vehicleNumber);
        return true;
    }

    public void isFull() {
        if(availableSlots.size() == 0)
            throw new ParkingLotException("Parking lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

}
