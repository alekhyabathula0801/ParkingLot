package com.parkinglotproblem.parkingservice;

import com.parkinglotproblem.airportmanagement.AirportSecurity;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;
import com.parkinglotproblem.parkingsystem.ParkingRepository;

public class ParkingService {

    public enum ParkingLotStatus {OPEN,CLOSED}

    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();

    private ParkingRepository parkingRepository;

    public void parkVehicle(Vehicle vehicle) {
        int position = parkingLotOwner.assignSlotNumber(parkingRepository.getOccupiedSlots(),parkingRepository.capacity);
        parkingRepository.parkVehicle(vehicle,position);
        if(parkingRepository.isFull()) {
            airportSecurity.getParkingLotStatus(true);
            parkingLotOwner.getParkingLotStatus(true);
        }
    }

    public int getParkingLotOccupiedSize() {
        return parkingRepository.getOccupiedSize();
    }

    public void unparkVehicle(Vehicle vehicle) {
        parkingRepository.unparkVehicle(vehicle);
        if(!parkingRepository.isFull())
            parkingLotOwner.getParkingLotStatus(false);
    }

    public int getSlotNumber(Vehicle vehicle) {
        return parkingRepository.getSlotNumber(vehicle);
    }

    public ParkingLotStatus getParkingLotStatus() {
        if(parkingRepository.isFull())
            return ParkingLotStatus.CLOSED;
        return ParkingLotStatus.OPEN;
    }

}
