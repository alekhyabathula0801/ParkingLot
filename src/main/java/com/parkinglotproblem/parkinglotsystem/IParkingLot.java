package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.awt.*;
import java.util.List;

public interface IParkingLot {

    public void setParkingSlots(ParkingSlot parkingSlot, ParkingSlot... parkingSlots);
    public boolean parkVehicle(Vehicle vehicle, ParkingLot.DriverType driverType);
    public int getParkingLotOccupiedSize();
    public boolean isVehicleParked(Vehicle vehicle);
    public boolean unparkVehicle(Vehicle vehicle);
    public ParkingSpot getParkingSpot(Vehicle vehicle);
    public ParkingLot.ParkingLotStatus getParkingLotStatus();
    public List<ParkingSpot> getAllVehiclesDataParkedInLot();
    public List<ParkingSpot> getVehiclesDataByColor(Color color);
    public List<ParkingSpot> getVehiclesDataByBrand(Vehicle.VehicleBrand vehicleBrand);
    public List<ParkingSpot> getVehiclesDataByColorAndBrand(Color color, Vehicle.VehicleBrand vehicleBrand);
    public List<ParkingSpot> getVehiclesDataInLastGivenMinutes(int time);
    public List<ParkingSpot> getVehiclesDataOfDriversInGivenSlots(ParkingLot.DriverType driverType, int slot1, int slot2);

}
