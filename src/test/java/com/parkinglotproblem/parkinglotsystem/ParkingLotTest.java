package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.ParkingLotStatus.CLOSED;
import static com.parkinglotproblem.parkinglotsystem.ParkingLot.ParkingLotStatus.OPEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParkingLotTest {

    ParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(new ParkingSlot(2),new ParkingSlot(3));
        parkingLot.parkVehicle(new Vehicle("AP10K0987"));
        parkingLot.parkVehicle(new Vehicle("KN90H1234"));
        parkingLot.parkVehicle(new Vehicle("AS90H1234"));
    }

    @Test
    public void given2Lots_when3VehiclesAdded_shouldReturnResults() {
        ParkingSpot vehicle1Details = parkingLot.getParkingSpot(new Vehicle("AP10K0987"));
        ParkingSpot vehicle2Details = parkingLot.getParkingSpot(new Vehicle("KN90H1234"));
        ParkingSpot vehicle3Details = parkingLot.getParkingSpot(new Vehicle("AS90H1234"));
        ParkingSpot expectedVehicle1Details = new ParkingSpot(0,1,new Vehicle("AP10K0987"));
        ParkingSpot expectedVehicle2Details = new ParkingSpot(1,1,new Vehicle("KN90H1234"));
        ParkingSpot expectedVehicle3Details = new ParkingSpot(0,2,new Vehicle("AS90H1234"));
        assertEquals(expectedVehicle1Details,vehicle1Details);
        assertEquals(expectedVehicle2Details,vehicle2Details);
        assertEquals(expectedVehicle3Details,vehicle3Details);
    }

    @Test
    public void given2Lots_when5VehiclesAdded_shouldReturnResults() {
        parkingLot.parkVehicle(new Vehicle("AS90H1233"));
        parkingLot.parkVehicle(new Vehicle("AS90H1230"));
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("AS90H1230"));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(1,3,new Vehicle("AS90H1230"));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void given2LotsWithDifferentSize_whenFull_shouldThrowException() {
        try {
            parkingLot.parkVehicle(new Vehicle("AP10K0981"));
            parkingLot.parkVehicle(new Vehicle("KN90H1233"));
            parkingLot.parkVehicle(new Vehicle("AS90H1233"));
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenParkingLotWith2Slots_whenVehicleUnparked_shouldReturnResults() {
        boolean status = parkingLot.unparkVehicle(new Vehicle("AS90H1234"));
        assertTrue(status);
    }

    @Test
    public void givenParkingLotWith2Slots_whenVehicleParked_shouldReturnResults() {
        boolean status = parkingLot.parkVehicle(new Vehicle("AS90H1239"));
        assertTrue(status);
    }

    @Test
    public void givenParkingLotWith2Slots_whenParked_shouldReturnSpotNumber() {
        int spotNumber = parkingLot.getSpotNumber(new Vehicle("AS90H1234"));
        assertEquals(2,spotNumber);
    }

    @Test
    public void givenParkingLotWith2Slots_when3VehiclesParked_shouldReturnOccupiedSize() {
        int size = parkingLot.getParkingLotOccupiedSize();
        assertEquals(3,size);
    }

    @Test
    public void givenParkingLot_whenFull_shouldReturnClosed() {
        parkingLot.parkVehicle(new Vehicle("AP10K0981"));
        parkingLot.parkVehicle(new Vehicle("KN90H1233"));
        ParkingLot.ParkingLotStatus status = parkingLot.getParkingLotStatus();
        assertEquals(CLOSED,status);
    }

    @Test
    public void givenParkingLot_whenNotFull_shouldReturnOpen() {
        parkingLot.parkVehicle(new Vehicle("AP10K0981"));
        ParkingLot.ParkingLotStatus status = parkingLot.getParkingLotStatus();
        assertEquals(OPEN,status);
    }

    @Test
    public void givenParkingLotWith2Slots_whenVehicleUnparkedAndOtherVehicleParked_shouldReturnResults() {
        parkingLot.unparkVehicle(new Vehicle("KN90H1234"));
        parkingLot.parkVehicle(new Vehicle("WA0K1230"));
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("WA0K1230"));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(1,1,new Vehicle("WA0K1230"));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

}
