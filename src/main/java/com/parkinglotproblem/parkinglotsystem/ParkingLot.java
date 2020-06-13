package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.airportmanagement.AirportSecurity;
import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParkingLot {

    enum DriverType {NORMAL,HANDICAPED}
    public enum ParkingLotStatus {OPEN,CLOSED}
    int parkingLotSize;
    List<ParkingSpot> parkingSpots = new ArrayList<>();
    List<ParkingSlot> parkingSlots = new ArrayList<>();
    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();

    public ParkingLot(ParkingSlot parkingSlot, ParkingSlot... parkingSlots) {
        this.parkingSlots.add(parkingSlot);
        this.parkingSlots.addAll(Arrays.asList(parkingSlots));
        this.parkingSlots.forEach(slot -> parkingLotSize += slot.capacity);
    }

    public boolean parkVehicle(Vehicle vehicle,DriverType driverType) {
        if(isFull())
            throw new ParkingLotException("Parking lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle Exists", ParkingLotException.ExceptionType.VEHICLE_EXISTS);
        parkingSpots.add(new ParkingFactory().parkVehicle(vehicle,parkingSlots,driverType));
        if(parkingSpots.size() == parkingLotSize) {
            new AirportSecurity().getParkingLotStatus(true);
            parkingLotOwner.getParkingLotStatus(true);
        }
        return true;
    }

    public int getParkingLotOccupiedSize() {
        return parkingSpots.size();
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingSpots.stream()
                           .anyMatch(spot -> spot.vehicle.equals(vehicle));
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        if(!isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle Doesn't Exists", ParkingLotException.ExceptionType.NO_VEHICLE);
        parkingSlots.get(getParkingSpot(vehicle).slotNumber).unparkVehicle(vehicle);
        parkingSpots.remove(getParkingSpot(vehicle));
        if(parkingSpots.size() == parkingLotSize-1)
            parkingLotOwner.getParkingLotStatus(false);
        return true;
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle) {
        return parkingSpots.stream()
                           .filter(spot -> spot.vehicle.equals(vehicle))
                           .findFirst()
                           .get();
    }

    public int getSpotNumber(Vehicle vehicle) {
        return parkingSlots.get(getParkingSpot(vehicle).slotNumber)
                           .getSpotNumber(vehicle);
    }

    public ParkingLotStatus getParkingLotStatus() {
        if(isFull())
            return ParkingLotStatus.CLOSED;
        return ParkingLotStatus.OPEN;
    }

    public boolean isFull() {
        return parkingSpots.size() == parkingLotSize;
    }

}
