package com.parkinglotproblem.parkingsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

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
        boolean result = parkingRepository.parkVehicle(new Vehicle("TS 09 K 2345"));
        assertTrue(result);
    }

    @Test
    public void givenCarsToPark_whenAdded_shouldReturnSize() {
        parkingRepository.parkVehicle(new Vehicle("TS 91 G 1267"));
        parkingRepository.parkVehicle(new Vehicle("MK 91 G 1245"));
        parkingRepository.parkVehicle(new Vehicle("JK 23 H 9807"));
        int size = parkingRepository.getOccupiedSize();
        assertEquals(3,size);
    }

    @Test
    public void givenCarsToPark_whenFull_shouldThrowException() {
        try {
            parkingRepository.parkVehicle(new Vehicle("TS 91 G 1267"));
            parkingRepository.parkVehicle(new Vehicle("MK 91 G 1245"));
            parkingRepository.parkVehicle(new Vehicle("JK 23 H 9807"));
            parkingRepository.parkVehicle(new Vehicle("TN 91 G 1267"));
            parkingRepository.parkVehicle(new Vehicle("KK 91 G 1245"));
            parkingRepository.parkVehicle(new Vehicle("GK 23 H 9807"));
        } catch (ParkingLotException e) {
            assertEquals(PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenCarsToPark_whenDataAlreadyExists_shouldThrowException() {
        try {
            parkingRepository.parkVehicle(new Vehicle("TS 91 G 1267"));
            parkingRepository.parkVehicle(new Vehicle("MK 91 G 1245"));
            parkingRepository.parkVehicle(new Vehicle("MK 91 G 1245"));
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
            parkingRepository.parkVehicle(new Vehicle(null));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenNull_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle(null);
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumber_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle(new Vehicle(""));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldDecreaseSizeBy1() {
        parkingRepository.parkVehicle(new Vehicle("TS 91 G 1267"));
        parkingRepository.parkVehicle(new Vehicle("MK 91 G 1245"));
        parkingRepository.parkVehicle(new Vehicle("JK 23 H 9807"));
        int size = parkingRepository.getOccupiedSize();
        parkingRepository.unparkVehicle(new Vehicle("JK 23 H 9807"));
        int updatedSize = parkingRepository.getOccupiedSize();
        assertEquals(size-1,updatedSize);
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldReturnTrue() {
        parkingRepository.parkVehicle(new Vehicle("TS 91 G 1267"));
        parkingRepository.parkVehicle(new Vehicle("MK 91 G 1245"));
        parkingRepository.parkVehicle(new Vehicle("JK 23 H 9807"));
        boolean result = parkingRepository.unparkVehicle(new Vehicle("JK 23 H 9807"));
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
            parkingRepository.unparkVehicle(new Vehicle("TS 09 CD 1234"));
        } catch (ParkingLotException e) {
            assertEquals(NO_VEHICLE,e.type);
        }
    }

}
