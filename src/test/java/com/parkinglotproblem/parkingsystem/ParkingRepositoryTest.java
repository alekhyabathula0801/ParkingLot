package com.parkinglotproblem.parkingsystem;

import org.junit.Before;
import org.junit.Test;

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
        boolean result = parkingRepository.addVehicle(Vehicle.CAR);
        assertTrue(result);
    }

    @Test
    public void givenCarsToPark_whenAdded_shouldReturnSize() {
        parkingRepository.addVehicle(Vehicle.CAR);
        parkingRepository.addVehicle(Vehicle.CAR);
        parkingRepository.addVehicle(Vehicle.CAR);
        int size = parkingRepository.getSize();
        assertEquals(3,size);
    }

    @Test
    public void givenNoVehicle_shouldReturnSize0() {
        int size = parkingRepository.getSize();
        assertEquals(0,size);
    }

}
