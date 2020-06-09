package com.parkinglotproblem.parkingsystem;

import org.junit.Before;
import org.junit.Test;

import static com.parkinglotproblem.parkingsystem.ParkingLotException.ExceptionType.ENTERED_EMPTY;
import static com.parkinglotproblem.parkingsystem.ParkingLotException.ExceptionType.ENTERED_NULL;
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
        boolean result = parkingRepository.addVehicle("TS 09 K 2345",new Customer("Ahana",VehicleType.CAR));
        assertTrue(result);
    }

    @Test
    public void givenCarsToPark_whenAdded_shouldReturnSize() {
        parkingRepository.addVehicle("TS 91 G 1267",new Customer("Kiran",VehicleType.CAR));
        parkingRepository.addVehicle("MK 91 G 1245",new Customer("Harbajan",VehicleType.CAR));
        parkingRepository.addVehicle("JK 23 H 9807",new Customer("Gita",VehicleType.CAR));
        int size = parkingRepository.getSize();
        assertEquals(3,size);
    }

    @Test
    public void givenNoVehicle_shouldReturnSize0() {
        int size = parkingRepository.getSize();
        assertEquals(0,size);
    }

    @Test
    public void givenNullNumber_shouldThrowExecption() {
        try {
            parkingRepository.addVehicle(null, new Customer("Ahana", VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenNullName_shouldThrowExecption() {
        try {
            parkingRepository.addVehicle("KH 09 W 1239", new Customer(null, VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_NULL,e.type);
        }
    }

    @Test
    public void givenEmptyCarNumber_shouldThrowExecption() {
        try {
            parkingRepository.addVehicle("", new Customer("Ahana", VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

    @Test
    public void givenEmptyName_shouldThrowExecption() {
        try {
            parkingRepository.addVehicle("TS 23 GT 2312", new Customer("", VehicleType.CAR));
        } catch (ParkingLotException e) {
            assertEquals(ENTERED_EMPTY,e.type);
        }
    }

}
