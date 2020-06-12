package com.parkinglotproblem.parkinglotsystem;

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
    EvenDistribution evenDistribution = new EvenDistribution();

    public ParkingLot(ParkingSlot parkingSlot, ParkingSlot... parkingSlots) {
        this.parkingSlots.add(parkingSlot);
        this.parkingSlots.addAll(Arrays.asList(parkingSlots));
        this.parkingSlots.forEach(slot -> parkingLotSize += slot.capacity);
    }

    public boolean parkVehicle(Vehicle vehicle,DriverType driverType) {
        if(DriverType.NORMAL.equals(driverType))
            return parkingSpots.add(evenDistribution.evenDistributionOfLots(vehicle,parkingSlots));
        return parkingSpots.add(new HandicapDriver().parkVehicle(vehicle,parkingSlots));
    }

    public int getParkingLotOccupiedSize() {
        return parkingSpots.size();
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        parkingSlots.get(getParkingSpot(vehicle).slotNumber).unparkVehicle(vehicle);
        return parkingSpots.remove(getParkingSpot(vehicle));
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle) {
        return parkingSpots.stream()
                           .filter(spot -> spot.vehicle.equals(vehicle))
                           .findFirst()
                           .get();
    }

    public int getSpotNumber(Vehicle vehicle) {
        return parkingSlots.get(getParkingSpot(vehicle).slotNumber).getSpotNumber(vehicle);
    }

    public ParkingLotStatus getParkingLotStatus() {
        if(parkingSpots.size() == parkingLotSize)
            return ParkingLotStatus.CLOSED;
        return ParkingLotStatus.OPEN;
    }

}
