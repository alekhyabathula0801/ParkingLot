package com.parkinglotproblem.parkinglotowner;

import java.util.List;

public class ParkingLotOwner {

    public boolean getParkingLotStatus(boolean status) {
        return status;
    }

    public int assignSpotNumber(List<Integer> occupiedPositions, int capacity){
        for (int position=1; position<=capacity; position++) {
            if(!occupiedPositions.contains(position))
                return position;
        }
        return -1;
    }

}
