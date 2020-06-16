package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.airportmanagement.AirportSecurity;
import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParkingLot implements IParkingLot{

    enum DriverType {NORMAL,HANDICAPED}
    public enum ParkingLotStatus {OPEN,CLOSED}
    int parkingLotSize;

    ParkingLotOwner parkingLotOwner;
    AirportSecurity airportSecurity;
    ParkingLotStrategy parkingLotStrategy;

    List<ParkingSlot> parkingSlots = new ArrayList<>();
    public ParkingLot(ParkingSlot parkingSlot, ParkingSlot... parkingSlots) {
        this.parkingSlots.add(parkingSlot);
        this.parkingSlots.addAll(Arrays.asList(parkingSlots));
        this.parkingSlots.forEach(slot -> parkingLotSize += slot.capacity);
        parkingLotOwner = new ParkingLotOwner();
        airportSecurity = new AirportSecurity();
        parkingLotStrategy = new ParkingLotStrategy();
    }

    public ParkingLot() {
    }

    public void setParkingSlots(ParkingSlot parkingSlot, ParkingSlot... parkingSlots) {
        this.parkingSlots.add(parkingSlot);
        this.parkingSlots.addAll(Arrays.asList(parkingSlots));
        this.parkingSlots.forEach(slot -> parkingLotSize += slot.capacity);
    }

    public boolean parkVehicle(Vehicle vehicle,DriverType driverType) {
        if(isFull())
            throw new ParkingLotException("Parking lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle Exists", ParkingLotException.ExceptionType.VEHICLE_EXISTS);
        if(parkingLotSize-getParkingLotOccupiedSize()<vehicle.vehicleSize.size)
            throw new ParkingLotException("Vehicle size is more han available spots",ParkingLotException.ExceptionType.PARKING_LOT_SIZE_IS_LOW);
        ParkingSpot parkingSpot = parkingLotStrategy.getParkingSpot(vehicle,parkingSlots,driverType);
        parkingSlots.get(parkingSpot.slotNumber).parkVehicle(parkingSpot);
        if(isFull()) {
            airportSecurity.getParkingLotStatus(true);
            parkingLotOwner.getParkingLotStatus(true);
        }
        return true;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        return parkingSlots.stream().anyMatch(parkingSlot -> parkingSlot.isVehicleParked(vehicle));
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        if(!isVehicleParked(vehicle))
            throw new ParkingLotException("Vehicle Doesn't Exists", ParkingLotException.ExceptionType.NO_VEHICLE);
        getParkingSlot(vehicle).unparkVehicle(vehicle);
        if(getParkingLotOccupiedSize() == parkingLotSize-1)
            parkingLotOwner.getParkingLotStatus(false);
        return true;
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle) {
        return getParkingSlot(vehicle).getParkingSpot(vehicle);
    }

    public ParkingSlot getParkingSlot(Vehicle vehicle) {
        return parkingSlots.stream()
                           .filter(parkingSlot -> parkingSlot.isVehicleParked(vehicle))
                           .findFirst()
                           .get();
    }

    public ParkingLotStatus getParkingLotStatus() {
        if(isFull())
            return ParkingLotStatus.CLOSED;
        return ParkingLotStatus.OPEN;
    }

    public int getParkingLotOccupiedSize() {
        return parkingSlots.stream()
                           .mapToInt(parkingSlot -> parkingSlot.parkingSlotData.size())
                           .sum();
    }

    public boolean isFull() {
        return getParkingLotOccupiedSize() == parkingLotSize;
    }

    public List<ParkingSpot> getParkingSpotsData() {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSlots.forEach(parkingSlot -> parkingSpots.addAll(parkingSlot.parkingSlotData.values()));
        return parkingSpots;
    }

    public List<ParkingSpot> getVehiclesDataByColor(Color color) {
        return getParkingSpotsData().stream()
                                    .filter(parkingSpot -> parkingSpot.vehicle.vehicleColor == color)
                                    .collect(Collectors.toList());
    }

    public List<ParkingSpot> getVehiclesDataByBrand(Vehicle.VehicleBrand vehicleBrand) {
        return getParkingSpotsData().stream()
                                    .filter(parkingSpot -> parkingSpot.vehicle.brand == vehicleBrand)
                                    .collect(Collectors.toList());
    }

    public List<ParkingSpot> getVehiclesDataByColorAndBrand(Color color, Vehicle.VehicleBrand vehicleBrand) {
        return getVehiclesDataByColor(color).stream()
                                            .filter(parkingSpot -> parkingSpot.vehicle.brand == vehicleBrand)
                                            .map(ParkingSpot::getDTO)
                                            .collect(Collectors.toList());
    }

    public List<ParkingSpot> getVehiclesDataInLastGivenMinutes(int time) {
        return getParkingSpotsData().stream()
                                    .filter(parkingSpot -> System.currentTimeMillis() - parkingSpot.inTime < time*60000)
                                    .collect(Collectors.toList());
    }

    public List<ParkingSpot> getVehiclesDataOfDriversInGivenSlots(DriverType driverType,int slot1, int slot2) {
        Predicate<ParkingSpot> driverInSlot1 = parkingSpot -> parkingSpot.driverType.equals(driverType)&& parkingSpot.slotNumber == slot1;
        Predicate<ParkingSpot> driverInSlot2 = parkingSpot -> parkingSpot.driverType.equals(driverType)&& parkingSpot.slotNumber == slot2;
        return getParkingSpotsData().stream()
                                    .filter(driverInSlot1.or(driverInSlot2))
                                    .collect(Collectors.toList());
    }

    public List<ParkingSpot> getAllVehiclesDataParkedInLot() {
        List<ParkingSpot> vehiclesData = new ArrayList<>();
        parkingSlots.forEach(parkingSlot -> vehiclesData.addAll(parkingSlot.parkingSpotsHistory));
        return vehiclesData;
    }

}
