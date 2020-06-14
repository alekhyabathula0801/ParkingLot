package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

public class ParkingFactory {

    public ParkingSpot parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots, ParkingLot.DriverType driverType) {
        if(vehicle.vehicleSize.equals(Vehicle.VehicleSize.LARGE))
            return new LargeVehicle().parkVehicle(vehicle,parkingSlots);
        if(ParkingLot.DriverType.NORMAL.equals(driverType))
            return new NormalDriver().parkVehicle(vehicle,parkingSlots);
        return new HandicapDriver().parkVehicle(vehicle,parkingSlots);
    }

}
