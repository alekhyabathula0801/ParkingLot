package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Objects;

public class ParkingSpot {

    public int slotNumber;
    public int spotNumber;
    public Vehicle vehicle;
    public long inTime;
    public long outTime;
    public ParkingLot.DriverType driverType;
    String vehicleNumber;

    public ParkingSpot(int slotNumber, int spotNumber, Vehicle vehicle, long inTime, ParkingLot.DriverType driverType) {
        this.slotNumber = slotNumber;
        this.spotNumber = spotNumber;
        this.vehicle = vehicle;
        this.inTime = inTime;
        this.driverType = driverType;
    }

    public ParkingSpot(int slotNumber, int spotNumber, String vehicleNumber) {
        this.slotNumber = slotNumber;
        this.spotNumber = spotNumber;
        this.vehicleNumber = vehicleNumber;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public ParkingSpot(int slotNumber, int spotNumber, Vehicle vehicle) {
        this.slotNumber = slotNumber;
        this.spotNumber = spotNumber;
        this.vehicle = vehicle;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return slotNumber == that.slotNumber &&
                spotNumber == that.spotNumber &&
                Objects.equals(vehicle, that.vehicle);
    }

    public ParkingSpot getDTO() {
        return new ParkingSpot(slotNumber,spotNumber,vehicle.vehicleNumber);
    }

}
