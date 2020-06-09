package com.parkinglotproblem.parkingservice;

import com.parkinglotproblem.parkingsystem.ParkingRepository;
import com.parkinglotproblem.parkingsystem.Vehicle;

public class ParkingService {

    private ParkingRepository parkingRepository;

    public void addVehicle(Vehicle vehicle) {
        parkingRepository.addVehicle(vehicle);
    }

    public int getParkingLotSize() {
        return parkingRepository.getSize();
    }

}
