package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.ParkingLotStatus.*;
import static com.parkinglotproblem.vehicle.Vehicle.VehicleBrand.*;
import static com.parkinglotproblem.vehicle.Vehicle.VehicleSize.*;
import static java.awt.Color.*;
import static org.junit.Assert.*;

public class ParkingLotTest {

    IParkingLot parkingLot;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(new ParkingSlot(2),new ParkingSlot(3));
        parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL, WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1234",SMALL,RED,BMW), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA), ParkingLot.DriverType.NORMAL);
    }

    @Test
    public void given2Lots_when3VehiclesAdded_shouldReturnResults() {
        ParkingSpot vehicle1Details = parkingLot.getParkingSpot(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA));
        ParkingSpot vehicle2Details = parkingLot.getParkingSpot(new Vehicle("KN90H1234",SMALL,RED,BMW));
        ParkingSpot vehicle3Details = parkingLot.getParkingSpot(new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA));
        ParkingSpot expectedVehicle1Details = new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle2Details = new ParkingSpot(1,1,new Vehicle("KN90H1234",SMALL,RED,BMW));
        ParkingSpot expectedVehicle3Details = new ParkingSpot(0,2,new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA));
        assertEquals(expectedVehicle1Details,vehicle1Details);
        assertEquals(expectedVehicle2Details,vehicle2Details);
        assertEquals(expectedVehicle3Details,vehicle3Details);
    }

    @Test
    public void given2Lots_when5VehiclesAdded_shouldReturnResults() {
        parkingLot.parkVehicle(new Vehicle("AS90H1233",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("AS90H1230",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("AS90H1230",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(1,3,new Vehicle("AS90H1230",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void given2LotsWithDifferentSize_whenFull_shouldThrowException() {
        try {
            parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle("KN90H1233",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle("AS90H1233",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

    @Test
    public void givenParkingLotWith2Slots_whenVehicleUnparked_shouldReturnResults() {
        boolean status = parkingLot.unparkVehicle(new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA));
        assertTrue(status);
    }

    @Test
    public void givenParkingLotWith2Slots_whenVehicleParked_shouldReturnResults() {
        boolean status = parkingLot.parkVehicle(new Vehicle("AS90H1239",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        assertTrue(status);
    }

    @Test
    public void givenParkingLotWith2Slots_when3VehiclesParked_shouldReturnOccupiedSize() {
        int size = parkingLot.getParkingLotOccupiedSize();
        assertEquals(3,size);
    }

    @Test
    public void givenParkingLot_whenFull_shouldReturnClosed() {
        parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1233",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingLot.ParkingLotStatus status = parkingLot.getParkingLotStatus();
        assertEquals(CLOSED,status);
    }

    @Test
    public void givenParkingLot_whenNotFull_shouldReturnOpen() {
        parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,RED,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingLot.ParkingLotStatus status = parkingLot.getParkingLotStatus();
        assertEquals(OPEN,status);
    }

    @Test
    public void givenVehicleToPark_whenIfPresent_shouldThrowException() {
        try {
            parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.VEHICLE_EXISTS,e.type);
        }
    }

    @Test
    public void givenParkingLotWith2Slots_whenVehicleUnparkedAndOtherVehicleParked_shouldReturnResults() {
        parkingLot.unparkVehicle(new Vehicle("KN90H1234",SMALL,WHITE,BMW));
        parkingLot.parkVehicle(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(1,1,new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void givenHandicapDriverVehicleToPark_shouldReturnResults() {
        parkingLot.parkVehicle(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.HANDICAPED);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(0,1,new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void givenVehicleToPark_whenHandicapDriver_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(2),new ParkingSlot(3));
        parkingLot.parkVehicle(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.HANDICAPED);
        ParkingSpot vehicle1Details = parkingLot.getParkingSpot(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle1Details = new ParkingSpot(0,1,new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle1Details,vehicle1Details);
    }

    @Test
    public void givenVehicleToPark_whenHandicapDriversToPark_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(2),new ParkingSlot(3));
        parkingLot.parkVehicle(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.HANDICAPED);
        parkingLot.parkVehicle(new Vehicle("KK09A5678",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("SK09A5679",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.HANDICAPED);
        ParkingSpot vehicle3Details = parkingLot.getParkingSpot(new Vehicle("SK09A5679",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle3Details = new ParkingSpot(0,2,new Vehicle("SK09A5679",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle3Details,vehicle3Details);
    }

    @Test
    public void givenHandicapDriverVehicleToPark_whenParked_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(2),new ParkingSlot(3));
        parkingLot.parkVehicle(new Vehicle("KK09A5678",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.HANDICAPED);
        ParkingSpot vehicle1Details = parkingLot.getParkingSpot(new Vehicle("KK09A5678",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle1Details = new ParkingSpot(1,1,new Vehicle("KK09A5678",SMALL,WHITE,TOYOTA));
        ParkingSpot vehicle2Details = parkingLot.getParkingSpot(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle2Details = new ParkingSpot(0,1,new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle1Details,vehicle1Details);
        assertEquals(expectedVehicle2Details,vehicle2Details);
    }

    @Test
    public void givenParkingLot_whenVehicleIsPresent_shouldReturnTrue() {
        assertTrue(parkingLot.isVehicleParked(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA)));
    }

    @Test
    public void givenParkingLot_whenVehicleIsNotPresent_shouldReturnFalse() {
        assertFalse(parkingLot.isVehicleParked(new Vehicle("AP10K0980",SMALL,WHITE,TOYOTA)));
    }

    @Test
    public void givenLargeVehicleToPark_whenParked_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(3));
        parkingLot.parkVehicle(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingSpot vehicle1Details = parkingLot.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        ParkingSpot expectedVehicle1Details = new ParkingSpot(0,1,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        assertEquals(expectedVehicle1Details,vehicle1Details);
    }

    @Test
    public void givenLargeAndSmallVehiclesToPark_whenParked_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(4));
        parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1234",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.unparkVehicle(new Vehicle("KN90H1234",SMALL,WHITE,TOYOTA));
        parkingLot.parkVehicle(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(0,3,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void givenLargeAndSmallVehicleToParkAndUnpark_whenParked_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(4));
        parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1234",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.unparkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA));
        parkingLot.parkVehicle(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(0,3,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void givenSmallVehicleToParkAndUnpark_whenLargeVehicleParked_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(2),new ParkingSlot(2));
        parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1234",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingSpot vehicle3Details = parkingLot.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        ParkingSpot expectedVehicle3Details = new ParkingSpot(0,1,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        assertEquals(expectedVehicle3Details,vehicle3Details);
    }

    @Test
    public void givenVehiclesToParkAndUnpark_whenLargeVehicleParked_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(2),new ParkingSlot(2));
        parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1234",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.parkVehicle(new Vehicle("JN90H1234",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        parkingLot.unparkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA));
        parkingLot.parkVehicle(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(0,1,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void givenLargeVehicleToPark_whenSpaceNotAvailable_shouldThrowException() {
        try {
            parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
            parkingLot.parkVehicle(new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_SIZE_IS_LOW,e.type);
        }
    }

    @Test
    public void givenSmallAndLargeVehicles_whenLargeVehicleUnparked_shouldReturnResults() {
        parkingLot.parkVehicle(new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA), ParkingLot.DriverType.NORMAL);
        int size = parkingLot.getParkingLotOccupiedSize();
        parkingLot.unparkVehicle(new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA));
        int updatedSize = parkingLot.getParkingLotOccupiedSize();
        assertEquals(size-2,updatedSize);
    }

    @Test
    public void givenVehiclesToPark_whenParked_shouldReturnWhiteColorVehiclesParkingSpot() {
        List<ParkingSpot> whiteColorVehicles = parkingLot.getVehiclesData(ParkingLot.FetchBy.WHITE);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA)));
        assertEquals(expectedList,whiteColorVehicles);
    }

    @Test
    public void givenVehiclesToPark_whenFetchForBlueToyota_shouldReturnResults() {
        List<ParkingSpot> buleToyotaVehicles = parkingLot.getVehiclesData(ParkingLot.FetchBy.BLUE_TOYATO);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(0,2,new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA)));
        assertEquals(expectedList,buleToyotaVehicles);
    }

    @Test
    public void givenVehiclesToPark_whenFetchForBMW_shouldReturnResults() {
        List<ParkingSpot> buleToyotaVehicles = parkingLot.getVehiclesData(ParkingLot.FetchBy.BMW);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(1,1,new Vehicle("KN90H1234",SMALL,RED,BMW)));
        assertEquals(expectedList,buleToyotaVehicles);
    }

    @Test
    public void givenVehiclesToPark_whenFetchVehiclesParkedInLast30Minutes_shouldReturnResults() {
        List<ParkingSpot> vehiclesParkedInLast30Minutes = parkingLot.getVehiclesData(ParkingLot.FetchBy.THIRTY_MINUTES);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA)));
        expectedList.add(new ParkingSpot(0,2,new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA)));
        expectedList.add(new ParkingSpot(1,1,new Vehicle("KN90H1234",SMALL,RED,BMW)));
        assertEquals(expectedList,vehiclesParkedInLast30Minutes);
    }

    @Test
    public void givenVehiclesToPark_whenFetchForHandicapedDriversInSlot2_shouldReturnResults() {
        IParkingLot parkingLot = new ParkingLot(new ParkingSlot(1),new ParkingSlot(1));
        parkingLot.parkVehicle(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.HANDICAPED);
        parkingLot.parkVehicle(new Vehicle("SK09A5679",SMALL,WHITE,TOYOTA), ParkingLot.DriverType.HANDICAPED);
        List<ParkingSpot> handicapedDriverVehicles = parkingLot.getVehiclesData(ParkingLot.FetchBy.HANDICAP_DRIVER_IN_SLOT1);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(1,1,new Vehicle("SK09A5679",SMALL,WHITE,TOYOTA)));
        assertEquals(expectedList,handicapedDriverVehicles);
    }

    @Test
    public void givenVehiclesToParkAndUnparked_shouldReturnAllVehiclesData() {
        parkingLot.unparkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA));
        List<ParkingSpot> vehiclesParkedInLot = parkingLot.getAllVehiclesDataParkedInLot();
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA)));
        expectedList.add(new ParkingSpot(0,2,new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA)));
        expectedList.add(new ParkingSpot(1,1,new Vehicle("KN90H1234",SMALL,RED,BMW)));
        assertEquals(expectedList,vehiclesParkedInLot);
    }

}
