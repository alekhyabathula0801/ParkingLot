package com.parkinglotproblem.parkingsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingRepository {

    int capacity;
    List<Vehicle> parkingDetails = new ArrayList<>();

    public ParkingRepository(int capacity) {
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if(isFull())
            throw new ParkingLotException("Parking lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        if(getIndex(vehicle.vehicleNumber) != -1 )
            throw new ParkingLotException("Vehicle Exists", ParkingLotException.ExceptionType.VEHICLE_EXISTS);
        parkingDetails.add(vehicle);
        return true;
    }

    private int getIndex(String vehicleNumber) {
        return IntStream.range(0,parkingDetails.size())
                .filter(index -> vehicleNumber.equals(this.parkingDetails.get(index).vehicleNumber))
                .findFirst()
                .orElse(-1);
    }

    public int getOccupiedSize() {
        return parkingDetails.size();
    }

    public boolean unparkVehicle(String vehicleNumber) {
        if(vehicleNumber == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(vehicleNumber.length() == 0)
            throw new ParkingLotException("Entered Empty", ParkingLotException.ExceptionType.ENTERED_EMPTY);
        int index = getIndex(vehicleNumber);
        if(index == -1)
            throw new ParkingLotException("Vehicle not present", ParkingLotException.ExceptionType.NO_VEHICLE);
        parkingDetails.remove(index);
        return true;
    }

    public boolean isFull() {
        if(parkingDetails.size() == capacity)
            return true;
        return false;
    }

}
