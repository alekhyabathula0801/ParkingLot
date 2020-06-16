package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

public class ParkingFactory {

    public ParkingSpot getParkingSpot(Vehicle vehicle, List<ParkingSlot> parkingSlots, ParkingLot.DriverType driverType) {
        if(vehicle.vehicleSize.equals(Vehicle.VehicleSize.LARGE))
            return new LargeVehicle().getParkingSpot(vehicle,parkingSlots,driverType);
        if(ParkingLot.DriverType.NORMAL.equals(driverType))
            return new NormalDriver().getParkingSpot(vehicle,parkingSlots);
        return new HandicapDriver().getParkingSpot(vehicle,parkingSlots);
    }

}
