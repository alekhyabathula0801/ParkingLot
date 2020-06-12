package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

public class ParkingFactory {

    public ParkingSpot parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots, ParkingLot.DriverType driverType) {
        if(ParkingLot.DriverType.NORMAL.equals(driverType))
            return new EvenDistribution().evenDistributionOfLots(vehicle,parkingSlots);
        return new HandicapDriver().parkVehicle(vehicle,parkingSlots);
    }

}
