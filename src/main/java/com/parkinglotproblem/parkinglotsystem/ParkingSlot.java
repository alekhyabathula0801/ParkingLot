package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.airportmanagement.AirportSecurity;
import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingSlot {

    public int capacity;
    Map<Integer,ParkingSpot> parkingSlotData = new HashMap<>();

    public ParkingSlot(int capacity) {
        this.capacity = capacity;
    }

    public boolean parkVehicle(ParkingSpot parkingSpot) {
        if(isFull())
            throw new ParkingLotException("Parking lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        if(isVehicleParked(parkingSpot.vehicle))
            throw new ParkingLotException("Vehicle Exists", ParkingLotException.ExceptionType.VEHICLE_EXISTS);
        if(parkingSlotData.containsKey(parkingSpot.position))
            throw new ParkingLotException("Slot occupied", ParkingLotException.ExceptionType.SLOT_OCCUPIED);
        parkingSlotData.put(parkingSpot.position,parkingSpot);
        return true;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingSlotData.entrySet()
                              .stream()
                              .anyMatch(element -> element.getValue().vehicle.equals(vehicle));
    }

    public int getOccupiedSize() {
        return parkingSlotData.size();
    }

    public List<Integer> getOccupiedSpots() {
        return new ArrayList<>(parkingSlotData.keySet());
    }

    public int getSpotNumber(Vehicle vehicle) {
        return parkingSlotData.entrySet()
                              .stream()
                              .filter(value -> value.getValue().vehicle.equals(vehicle))
                              .map(Map.Entry::getKey)
                              .findFirst()
                              .get();
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        if(vehicle == null)
            throw new ParkingLotException("Entered Null", ParkingLotException.ExceptionType.ENTERED_NULL);
        if(!isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle not present", ParkingLotException.ExceptionType.NO_VEHICLE);
        parkingSlotData.remove(getSpotNumber(vehicle));
        return true;
    }

    public boolean isFull() {
        return parkingSlotData.size() == capacity;
    }

}
