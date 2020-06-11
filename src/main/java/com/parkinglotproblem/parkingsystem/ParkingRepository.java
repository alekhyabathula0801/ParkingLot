package com.parkinglotproblem.parkingsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingRepository {

    public int capacity;
    Map<Integer,Vehicle> parkingData = new HashMap<>();

    public ParkingRepository(int capacity) {
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean parkVehicle(Vehicle vehicle,int position) {
        if(isFull())
            throw new ParkingLotException("Parking lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        if(vehicle == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle Exists", ParkingLotException.ExceptionType.VEHICLE_EXISTS);
        if(parkingData.containsKey(position))
            throw new ParkingLotException("Slot occupied", ParkingLotException.ExceptionType.SLOT_OCCUPIED);
        vehicle.setInTime(System.currentTimeMillis());
        parkingData.put(position,vehicle);
        return true;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingData.entrySet().stream().anyMatch(element -> element.getValue().equals(vehicle));
    }

    public int getOccupiedSize() {
        return parkingData.size();
    }

    public List<Integer> getOccupiedSlots() {
        return new ArrayList<>(parkingData.keySet());
    }

    public int getSlotNumber(Vehicle vehicle) {
        return parkingData.entrySet().stream()
                          .filter(value -> value.getValue().equals(vehicle))
                          .map(Map.Entry::getKey)
                          .findFirst()
                          .get();
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(!isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle not present", ParkingLotException.ExceptionType.NO_VEHICLE);
        parkingData.remove(getSlotNumber(vehicle));
        return true;
    }

    public boolean isFull() {
        return parkingData.size() == capacity;
    }

}
