package com.parkinglotproblem.parkingservice;

import com.parkinglotproblem.parkingsystem.Customer;
import com.parkinglotproblem.parkingsystem.ParkingRepository;

public class ParkingService {

    public enum ParkingLotStatus {OPEN,CLOSED}

    int MAXIMUM_CAPACITY = 100;

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

    public ParkingLotStatus getParkingLotStatus() {
        if(getParkingLotSize() == MAXIMUM_CAPACITY)
            return ParkingLotStatus.CLOSED;
        return ParkingLotStatus.OPEN;
    }

}
