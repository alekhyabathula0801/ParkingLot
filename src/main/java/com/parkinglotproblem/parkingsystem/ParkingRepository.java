package com.parkinglotproblem.parkingsystem;

import java.util.ArrayList;
import java.util.List;

public class ParkingRepository {

    List<Vehicle> vehicles = new ArrayList<>();

    public boolean addVehicle(Vehicle vehicle) {
        return vehicles.add(vehicle);
    }

    public int getSize() {
        return vehicles.size();
    }

}
