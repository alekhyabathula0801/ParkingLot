package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

public interface IParkingLot {

    public boolean parkVehicle(Vehicle vehicle, ParkingLot.DriverType driverType);
    public int getParkingLotOccupiedSize();
    public boolean isVehicleParked(Vehicle vehicle);
    public boolean unparkVehicle(Vehicle vehicle);
    public ParkingSpot getParkingSpot(Vehicle vehicle);
    public ParkingLot.ParkingLotStatus getParkingLotStatus();
    public List<ParkingSpot> getVehiclesData(ParkingLot.FetchBy fetchBy);

}
