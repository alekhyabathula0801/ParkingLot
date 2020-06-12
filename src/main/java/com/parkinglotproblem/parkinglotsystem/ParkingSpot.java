package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Objects;

public class ParkingSpot {

    public int slotNumber;
    public int position;
    public Vehicle vehicle;
    public long inTime;

    public ParkingSpot(int slotNumber, int position, Vehicle vehicle, long inTime) {
        this.slotNumber = slotNumber;
        this.position = position;
        this.vehicle = vehicle;
        this.inTime = inTime;
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

}
