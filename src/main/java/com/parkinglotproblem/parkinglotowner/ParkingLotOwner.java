package com.parkinglotproblem.parkinglotowner;

import com.parkinglotproblem.exception.ParkingLotException;

import java.util.List;

public class ParkingLotOwner {

    public boolean getParkingLotStatus(boolean status) {
        return status;
    }

    public int assignSlotNumber(List<Integer> occupiedSlots, int capacity){
        for (int slot=1; slot<=capacity; slot++) {
            if(!occupiedSlots.contains(slot))
                return slot;
        }
        throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

}
