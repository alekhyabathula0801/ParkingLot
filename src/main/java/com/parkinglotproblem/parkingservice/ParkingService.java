package com.parkinglotproblem.parkingservice;

import com.parkinglotproblem.parkingsystem.Customer;
import com.parkinglotproblem.parkingsystem.ParkingRepository;

public class ParkingService {

    private ParkingRepository parkingRepository;

    public void parkVehicle(String vehicleNumber, Customer customer) {
        parkingRepository.parkVehicle(vehicleNumber,customer);
    }

    public int getParkingLotSize() {
        return parkingRepository.getSize();
    }

    public void unparkVehicle(String vehcileNumber) {
        parkingRepository.unparkVehicle(vehcileNumber);
    }

}
