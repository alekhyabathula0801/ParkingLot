package com.parkinglotproblem.parkingsystem;

import org.junit.Before;
import org.junit.Test;

import static com.parkinglotproblem.parkingsystem.ParkingLotException.ExceptionType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParkingRepositoryTest {

    ParkingRepository parkingRepository;

    @Before
    public void setUp() throws Exception {
        parkingRepository = new ParkingRepository();
    }

    @Test
    public void givenCarToPark_whenAdded_shouldReturnTrue() {
        boolean result = parkingRepository.parkVehicle("TS 09 K 2345",new Customer("Ahana",VehicleType.CAR));
        assertTrue(result);
    }

    @Test
    public void givenCarsToPark_whenAdded_shouldReturnSize() {
        parkingRepository.parkVehicle("TS 91 G 1267",new Customer("Kiran",VehicleType.CAR));
        parkingRepository.parkVehicle("MK 91 G 1245",new Customer("Harbajan",VehicleType.CAR));
        parkingRepository.parkVehicle("JK 23 H 9807",new Customer("Gita",VehicleType.CAR));
        int size = parkingRepository.getSize();
        assertEquals(3,size);
    }

    @Test
    public void givenCarsToPark_whenDataAlreadyExists_shouldThrowException() {
        try {
            parkingRepository.parkVehicle("TS 91 G 1267", new Customer("Kiran", VehicleType.CAR));
            parkingRepository.parkVehicle("MK 91 G 1245", new Customer("Harbajan", VehicleType.CAR));
            parkingRepository.parkVehicle("MK 91 G 1245", new Customer("Gita", VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(DATA_EXISTS,e.type);
        }
    }

    @Test
    public void givenNoVehicle_shouldReturnSize0() {
        int size = parkingRepository.getSize();
        assertEquals(0,size);
    }

    @Test
    public void givenNullNumber_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle(null, new Customer("Ahana", VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenNullName_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle("KH 09 W 1239", new Customer(null, VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumber_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle("", new Customer("Ahana", VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

    @Test
    public void givenEmptyName_shouldThrowExecption() {
        try {
            parkingRepository.parkVehicle("TS 23 GT 2312", new Customer("", VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldDecreaseSizeBy1() {
        parkingRepository.parkVehicle("TS 91 G 1267",new Customer("Kiran",VehicleType.CAR));
        parkingRepository.parkVehicle("MK 91 G 1245",new Customer("Harbajan",VehicleType.CAR));
        parkingRepository.parkVehicle("JK 23 H 9807",new Customer("Gita",VehicleType.CAR));
        int size = parkingRepository.getSize();
        parkingRepository.unparkVehicle("JK 23 H 9807");
        int updatedSize = parkingRepository.getSize();
        assertEquals(size-1,updatedSize);
    }

    @Test
    public void givenCarToUnpark_whenRemoved_shouldReturnTrue() {
        parkingRepository.parkVehicle("TS 91 G 1267",new Customer("Kiran",VehicleType.CAR));
        parkingRepository.parkVehicle("MK 91 G 1245",new Customer("Harbajan",VehicleType.CAR));
        parkingRepository.parkVehicle("JK 23 H 9807",new Customer("Gita",VehicleType.CAR));
        boolean result = parkingRepository.unparkVehicle("JK 23 H 9807");
        assertTrue(result);
    }

    @Test
    public void givenNullNumberToUnpark_shouldThrowExecption() {
        try {
            parkingRepository.unparkVehicle(null);
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumberToUnpark_shouldThrowExecption() {
        try {
            parkingRepository.unparkVehicle("");
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

    @Test
    public void givenCarNumberToUnpark_whenNotPresent_shouldThrowExecption() {
        try {
            parkingRepository.unparkVehicle("TS 09 CD 1234");
        } catch (ParkingLotException e) {
            assertEquals(DATA_DOESNT_EXISTS,e.type);
        }
    }

}
