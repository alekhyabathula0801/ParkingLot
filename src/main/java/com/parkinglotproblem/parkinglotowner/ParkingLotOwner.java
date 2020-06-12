package com.parkinglotproblem.parkinglotowner;

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
        return -1;
    }

}
