package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Comparator;
import java.util.List;

public class NormalDriver {

    public ParkingSpot parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        ParkingSlot parkingSlot = parkingSlots.stream()
                                              .filter(ParkingSlot::isOpen)
                                              .min(Comparator.comparing(ParkingSlot::getOccupiedSize))
                                              .get();
        int slotNumber = parkingSlots.indexOf(parkingSlot);
        int spotNumber = parkingSlot.getUnoccupiedSpots().get(0);
        ParkingSpot parkingSpot = new ParkingSpot(slotNumber,spotNumber,vehicle,System.currentTimeMillis(), ParkingLot.DriverType.NORMAL);
        parkingSlots.get(slotNumber).parkVehicle(parkingSpot);
        return parkingSpot;
    }

}
