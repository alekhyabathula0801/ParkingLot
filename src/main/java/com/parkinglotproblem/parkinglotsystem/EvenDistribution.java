package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglotproblem.exception.ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL;

public class EvenDistribution {

    ParkingLotOwner parkingLotOwner = new ParkingLotOwner();

    public ParkingSpot evenDistributionOfLots(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        List<Integer> spots = new ArrayList<>(parkingSlots.size());

        for (ParkingSlot parkingSlot : parkingSlots)
            spots.add(parkingLotOwner.assignSlotNumber(parkingSlot.getOccupiedSlots(),parkingSlot.capacity));

        int leastSlotValue = spots.stream()
                                  .filter(slot -> slot>0)
                                  .mapToInt(value -> value)
                                  .min()
                                  .orElseThrow(() -> new ParkingLotException("Parking lots are full",PARKING_LOT_IS_FULL));
        int indexOfLeastSlotValue = spots.indexOf(leastSlotValue);
        parkingSlots.get(indexOfLeastSlotValue).parkVehicle(vehicle,leastSlotValue);
        return new ParkingSpot(indexOfLeastSlotValue,leastSlotValue,vehicle,System.currentTimeMillis());
    }

}
