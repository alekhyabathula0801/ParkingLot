package com.parkinglotproblem.parkinglotowner;

import com.parkinglotproblem.exception.ParkingLotException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void givenOccupiedSlotsListsAndCapacity_shouldReturnAvailableSlotNumber() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        List<Integer> slot = new ArrayList<>();
        slot.add(1);
        slot.add(2);
        int slots = parkingLotOwner.assignSlotNumber(slot,5);
        assertEquals(3,slots);
    }

    @Test
    public void givenOccupiedSlotsListsAndCapacity_whenParkingLotIsFull_shouldThrowException() {
        try {
            ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
            List<Integer> slot = new ArrayList<>();
            slot.add(1);
            slot.add(2);
            parkingLotOwner.assignSlotNumber(slot, 2);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

}
