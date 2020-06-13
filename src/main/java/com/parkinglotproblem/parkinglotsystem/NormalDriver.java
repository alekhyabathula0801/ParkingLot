package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglotproblem.exception.ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL;

public class NormalDriver {

    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();

    public ParkingSpot parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        List<Integer> spots = new ArrayList<>(parkingSlots.size());

        for (ParkingSlot parkingSlot : parkingSlots)
            spots.add(parkingLotOwner.assignSpotNumber(parkingSlot.getOccupiedSpots(),parkingSlot.capacity));

        int spotNumber = spots.stream()
                              .filter(position -> position>0)
                              .mapToInt(value -> value)
                              .min()
                              .orElseThrow(() -> new ParkingLotException("Parking lots are full",PARKING_LOT_IS_FULL));
        int slotNumber = spots.indexOf(spotNumber);
        ParkingSpot parkingSpot = new ParkingSpot(slotNumber,spotNumber,vehicle,System.currentTimeMillis(), ParkingLot.DriverType.NORMAL);
        parkingSlots.get(slotNumber).parkVehicle(parkingSpot);
        return parkingSpot;
    }

}
