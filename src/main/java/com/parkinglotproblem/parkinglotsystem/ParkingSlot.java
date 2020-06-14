package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingSlot {

    public int capacity;
    public Map<Integer,ParkingSpot> parkingSlotData = new HashMap<>();

    public ParkingSlot(int capacity) {
        this.capacity = capacity;
    }

    public boolean parkVehicle(ParkingSpot parkingSpot) {
        if(isFull())
            throw new ParkingLotException("Parking lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        if(isVehicleParked(parkingSpot.vehicle))
            throw new ParkingLotException("Vehicle Exists", ParkingLotException.ExceptionType.VEHICLE_EXISTS);
        if(parkingSlotData.containsKey(parkingSpot.spotNumber))
            throw new ParkingLotException("Slot occupied", ParkingLotException.ExceptionType.SLOT_OCCUPIED);
        int spotNumber = parkingSpot.spotNumber;
        for(int index=0; index<parkingSpot.vehicle.vehicleSize.size; index++) {
            parkingSlotData.put(spotNumber, parkingSpot);
            spotNumber++;
        }
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

    public List<Integer> getUnoccupiedSpots() {
        List<Integer> unoccupiedSpots = new ArrayList<>();
        for(int spot=1; spot<=capacity; spot++) {
            if(!parkingSlotData.containsKey(spot))
                unoccupiedSpots.add(spot);
        }
        return unoccupiedSpots;
    }

    public boolean isOpen() {
        return !isFull();
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
        for(int index=0; index<vehicle.vehicleSize.size; index++)
            parkingSlotData.remove(getSpotNumber(vehicle));
        return true;
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle) {
        return parkingSlotData.get(getSpotNumber(vehicle));
    }

    public List<ParkingSpot> getParkingSpotData() {
        return new ArrayList<>(parkingSlotData.values());
    }

    public boolean isFull() {
        return parkingSlotData.size() == capacity;
    }

}
