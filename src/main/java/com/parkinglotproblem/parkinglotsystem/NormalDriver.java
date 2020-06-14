package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Comparator;
import java.util.List;

public class NormalDriver {

    public boolean parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        ParkingSlot parkingSlot = parkingSlots.stream()
                                              .filter(ParkingSlot::isOpen)
                                              .min(Comparator.comparing(ParkingSlot::getOccupiedSize))
                                              .get();
        int slotNumber = parkingSlots.indexOf(parkingSlot);
        return parkingSlots.get(slotNumber).parkVehicle(new ParkingSpot(slotNumber,parkingSlot.getUnoccupiedSpots().get(0),
                                                        vehicle,System.currentTimeMillis(), ParkingLot.DriverType.NORMAL));
    }

}
