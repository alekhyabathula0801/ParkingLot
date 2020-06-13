package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglotproblem.exception.ParkingLotException.ExceptionType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParkingSlotTest {

    ParkingSlot parkingSlot;

    @Before
    public void setUp() throws Exception {
        parkingSlot = new ParkingSlot(5);
    }

    @Test
    public void givenCarToPark_whenAdded_shouldReturnTrue() {
        boolean result = parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS09K2345")));
        assertTrue(result);
    }

    @Test
    public void givenCarsToPark_whenAdded_shouldReturnSize() {
        parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
        parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("MK91G1245")));
        parkingSlot.parkVehicle(new ParkingSpot(0,3,new Vehicle("JK23H9807")));
        int size = parkingSlot.getOccupiedSize();
        assertEquals(3,size);
    }

    @Test
    public void givenCarsToPark_whenFull_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
            parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("MK91G1245")));
            parkingSlot.parkVehicle(new ParkingSpot(0,3,new Vehicle("JK23H9807")));
            parkingSlot.parkVehicle(new ParkingSpot(0,4,new Vehicle("TS91G1268")));
            parkingSlot.parkVehicle(new ParkingSpot(0,5,new Vehicle("MK91G1246")));
            parkingSlot.parkVehicle(new ParkingSpot(0,6,new Vehicle("JK23H9800")));
        } catch (ParkingLotException e) {
            assertEquals(PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenCarsToPark_whenSameSlot_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
            parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("MK91G1245")));
            parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("JK23H9807")));
        } catch (ParkingLotException e) {
            assertEquals(SLOT_OCCUPIED,e.type);
        }
    }

    @Test
    public void givenCarsToPark_whenDataAlreadyExists_shouldThrowException() {
        try {
            parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
            parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("MK91G1245")));
            parkingSlot.parkVehicle(new ParkingSpot(0,3,new Vehicle("MK91G1245")));
        } catch (ParkingLotException e) {
            assertEquals(VEHICLE_EXISTS,e.type);
        }
    }

    @Test
    public void givenNoVehicle_shouldReturnSize0() {
        int size = parkingSlot.getOccupiedSize();
        assertEquals(0,size);
    }

    @Test
    public void givenNullNumber_shouldThrowExecption() {
        try {
            parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle(null)));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenNull_shouldThrowExecption() {
        try {
            parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle(null)));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumber_shouldThrowExecption() {
        try {
            parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("")));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldDecreaseSizeBy1() {
        parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
        parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("MK91G1245")));
        parkingSlot.parkVehicle(new ParkingSpot(0,3,new Vehicle("JK23H9807")));
        int size = parkingSlot.getOccupiedSize();
        parkingSlot.unparkVehicle(new Vehicle("JK23H9807"));
        int updatedSize = parkingSlot.getOccupiedSize();
        assertEquals(size-1,updatedSize);
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldReturnTrue() {
        parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
        parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("MK91G1245")));
        parkingSlot.parkVehicle(new ParkingSpot(0,3,new Vehicle("JK23H9807")));
        boolean result = parkingSlot.unparkVehicle(new Vehicle("JK23H9807"));
        assertTrue(result);
    }

    @Test
    public void givenNullNumberToUnpark_shouldThrowExecption() {
        try {
            parkingSlot.unparkVehicle(new Vehicle(null));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenNullToUnpark_shouldThrowExecption() {
        try {
            parkingSlot.unparkVehicle(null);
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumberToUnpark_shouldThrowExecption() {
        try {
            parkingSlot.unparkVehicle(new Vehicle(""));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);

        }
    }

    @Test
    public void givenCarNumberToUnpark_whenNotPresent_shouldThrowExecption() {
        try {
            parkingSlot.unparkVehicle(new Vehicle("TS09CD1234"));
        } catch (ParkingLotException e) {
            assertEquals(NO_VEHICLE,e.type);
        }
    }

    @Test
    public void givenVehicleToPark_whenParked_ReturnSlotNumber() {
        parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
        int slotNumber = parkingSlot.getSpotNumber(new Vehicle("TS91G1267"));
        assertEquals(1,slotNumber);
    }

    @Test
    public void givenVehiclesToPark_whenParked_shouldReturnOccupiedSlots() {
        List<Integer> expectedSlots = new ArrayList<>();
        expectedSlots.add(1);
        expectedSlots.add(2);
        expectedSlots.add(3);
        parkingSlot.parkVehicle(new ParkingSpot(0,1,new Vehicle("TS91G1267")));
        parkingSlot.parkVehicle(new ParkingSpot(0,2,new Vehicle("MK91G1245")));
        parkingSlot.parkVehicle(new ParkingSpot(0,3,new Vehicle("JK23H9807")));
        List<Integer> occupiedSlots = parkingSlot.getOccupiedSlots();
        assertEquals(expectedSlots,occupiedSlots);
    }

}
