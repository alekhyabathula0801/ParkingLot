package com.parkinglotproblem.parkinglotowner;

import org.junit.Test;

import static org.junit.Assert.*;

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
