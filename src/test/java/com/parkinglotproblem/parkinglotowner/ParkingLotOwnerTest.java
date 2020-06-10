package com.parkinglotproblem.parkinglotowner;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParkingLotOwnerTest {

    @Test
    public void givenParkingLotStatus_whenFull_shouldReturnTrue() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        boolean status = parkingLotOwner.getParkingLotStatus(true);
        assertTrue(status);
    }

    @Test
    public void givenParkingLotStatus_whenNotFull_shouldReturnFalse() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        boolean status = parkingLotOwner.getParkingLotStatus(false);
        assertFalse(status);
    }

}
