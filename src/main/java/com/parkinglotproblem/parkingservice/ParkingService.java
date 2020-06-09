package com.parkinglotproblem.parkingservice;

import com.parkinglotproblem.parkingsystem.Customer;
import com.parkinglotproblem.parkingsystem.ParkingRepository;

public class ParkingService {

    private ParkingRepository parkingRepository;

    public void addVehicle(String vehcileNumber,Customer customer) {
        parkingRepository.addVehicle(vehcileNumber,customer);
    }

    public int getParkingLotSize() {
        return parkingRepository.getSize();
    }

}
