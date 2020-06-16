package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

public class ParkingLotStrategy {

    public ParkingSpot getParkingSpot(Vehicle vehicle, List<ParkingSlot> parkingSlots, ParkingLot.DriverType driverType) {
        if(vehicle.vehicleSize.equals(Vehicle.VehicleSize.LARGE))
            return new LargeVehicleStrategy().getParkingSpot(vehicle,parkingSlots,driverType);
        if(ParkingLot.DriverType.NORMAL.equals(driverType))
            return new NormalDriverStrategy().getParkingSpot(vehicle,parkingSlots);
        return new HandicapDriverStrategy().getParkingSpot(vehicle,parkingSlots);
    }

}
