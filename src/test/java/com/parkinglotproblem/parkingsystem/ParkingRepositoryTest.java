package com.parkinglotproblem.parkingsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglotproblem.exception.ParkingLotException.ExceptionType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParkingRepositoryTest {

    ParkingRepository parkingRepository;

    @Before
    public void setUp() throws Exception {
        parkingRepository = new ParkingRepository(5);
    }

    @Test
    public void givenCarToPark_whenAdded_shouldReturnTrue() {
        boolean result = parkingRepository.parkVehicle(new Vehicle("TS09K2345"),1);
        assertTrue(result);
    }

    @Test
    public void givenCarsToPark_whenAdded_shouldReturnSize() {
        parkingRepository.parkVehicle(new Vehicle("TS91G1267"),1);
        parkingRepository.parkVehicle(new Vehicle("MK91G1245"),2);
        parkingRepository.parkVehicle(new Vehicle("JK23H9807"),3);
        int size = parkingRepository.getOccupiedSize();
        assertEquals(3,size);
    }

    @Test
    public void givenCarsToPark_whenFull_shouldThrowException() {
        try {
            parkingRepository.parkVehicle(new Vehicle("TS91G1267"),1);
            parkingRepository.parkVehicle(new Vehicle("MK91G1245"),2);
            parkingRepository.parkVehicle(new Vehicle("JK23H9807"),3);
            parkingRepository.parkVehicle(new Vehicle("TN91G1267"),4);
            parkingRepository.parkVehicle(new Vehicle("KK91G1245"),5);
            parkingRepository.parkVehicle(new Vehicle("GK23H9807"),6);
        } catch (ParkingLotException e) {
            assertEquals(PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenCarsToPark_whenSameSlot_shouldThrowException() {
        try {
            parkingRepository.parkVehicle(new Vehicle("TS91G1267"),1);
            parkingRepository.parkVehicle(new Vehicle("MK91G1245"),2);
            parkingRepository.parkVehicle(new Vehicle("JK23H9807"),2);
        } catch (ParkingLotException e) {
            assertEquals(SLOT_OCCUPIED,e.type);
        }
    }

    @Test
    public void givenCarsToPark_whenDataAlreadyExists_shouldThrowException() {
        try {
            parkingRepository.parkVehicle(new Vehicle("TS91G1267"),1);
            parkingRepository.parkVehicle(new Vehicle("MK91G1245"),2);
            parkingRepository.parkVehicle(new Vehicle("MK91G1245"),3);
        } catch (ParkingLotException e) {
            assertEquals(VEHICLE_EXISTS,e.type);
        }
    }

    @Test
    public void givenNoVehicle_shouldReturnSize0() {
        int size = parkingRepository.getOccupiedSize();
        assertEquals(0,size);
    }

    @Test
    public void givenNullNumber_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle(new Vehicle(null),1);
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenNull_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle(null,1);
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumber_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle(new Vehicle(""),1);
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldDecreaseSizeBy1() {
        parkingRepository.parkVehicle(new Vehicle("TS91G1267"),1);
        parkingRepository.parkVehicle(new Vehicle("MK91G1245"),2);
        parkingRepository.parkVehicle(new Vehicle("JK23H9807"),3);
        int size = parkingRepository.getOccupiedSize();
        parkingRepository.unparkVehicle(new Vehicle("JK23H9807"));
        int updatedSize = parkingRepository.getOccupiedSize();
        assertEquals(size-1,updatedSize);
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldReturnTrue() {
        parkingRepository.parkVehicle(new Vehicle("TS91G1267"),1);
        parkingRepository.parkVehicle(new Vehicle("MK91G1245"),2);
        parkingRepository.parkVehicle(new Vehicle("JK23H9807"),3);
        boolean result = parkingRepository.unparkVehicle(new Vehicle("JK23H9807"));
        assertTrue(result);
    }

    @Test
    public void givenNullNumberToUnpark_shouldThrowExecption() {
        try {
            parkingRepository.unparkVehicle(new Vehicle(null));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenNullToUnpark_shouldThrowExecption() {
        try {
            parkingRepository.unparkVehicle(null);
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumberToUnpark_shouldThrowExecption() {
        try {
            parkingRepository.unparkVehicle(new Vehicle(""));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);

        }
    }

    @Test
    public void givenCarNumberToUnpark_whenNotPresent_shouldThrowExecption() {
        try {
            parkingRepository.unparkVehicle(new Vehicle("TS09CD1234"));
        } catch (ParkingLotException e) {
            assertEquals(NO_VEHICLE,e.type);
        }
    }

    @Test
    public void givenVehicleToPark_whenParked_ReturnSlotNumber() {
        parkingRepository.parkVehicle(new Vehicle("AP01GH2345"),5);
        int slotNumber = parkingRepository.getSlotNumber(new Vehicle("AP01GH2345"));
        assertEquals(5,slotNumber);
    }

    @Test
    public void givenVehiclesToPark_whenParked_shouldReturnOccupiedSlots() {
        List<Integer> expectedSlots = new ArrayList<>();
        expectedSlots.add(1);
        expectedSlots.add(2);
        expectedSlots.add(3);
        parkingRepository.parkVehicle(new Vehicle("TS91G1267"),1);
        parkingRepository.parkVehicle(new Vehicle("MK91G1245"),2);
        parkingRepository.parkVehicle(new Vehicle("JK23H9807"),3);
        List<Integer> occupiedSlots = parkingRepository.getOccupiedSlots();
        assertEquals(expectedSlots,occupiedSlots);
    }

}
