package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Objects;

public class ParkingSpot {

    public int slotNumber;
    public int position;
    public Vehicle vehicle;
    public long inTime;
    public ParkingLot.DriverType driverType;

    public ParkingSpot(int slotNumber, int position, Vehicle vehicle, long inTime, ParkingLot.DriverType driverType) {
        this.slotNumber = slotNumber;
        this.position = position;
        this.vehicle = vehicle;
        this.inTime = inTime;
        this.driverType = driverType;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public ParkingSpot(int slotNumber, int position, Vehicle vehicle) {
        this.slotNumber = slotNumber;
        this.position = position;
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return slotNumber == that.slotNumber &&
                position == that.position &&
                Objects.equals(vehicle, that.vehicle);
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
