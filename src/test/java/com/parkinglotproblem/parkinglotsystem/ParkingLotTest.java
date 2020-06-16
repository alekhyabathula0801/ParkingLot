package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.airportmanagement.AirportSecurity;
import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.DriverType.*;
import static com.parkinglotproblem.parkinglotsystem.ParkingLot.ParkingLotStatus.*;
import static com.parkinglotproblem.vehicle.Vehicle.VehicleBrand.*;
import static com.parkinglotproblem.vehicle.Vehicle.VehicleSize.*;
import static java.awt.Color.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotTest {

    @InjectMocks
    IParkingLot parkingLot = new ParkingLot();

    @Mock
    ParkingLotOwner parkingLotOwner;

    @Mock
    ParkingFactory parkingFactory;

    @Mock
    AirportSecurity airportSecurity;

    List<ParkingSlot> slots;

    @Before
    public void setUp() throws Exception {
        ParkingSlot parkingSlot1 = new ParkingSlot(2);
        ParkingSlot parkingSlot2 = new ParkingSlot(3);
        parkingLot.setParkingSlots(parkingSlot1,parkingSlot2);
        slots = new ArrayList<>();
        slots.add(parkingSlot1);
        slots.add(parkingSlot2);
        when(parkingLotOwner.getParkingLotStatus(true)).thenReturn(true);
        when(airportSecurity.getParkingLotStatus(true)).thenReturn(true);
        when(parkingFactory.getParkingSpot(new Vehicle("AP10K0987",SMALL, WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),NORMAL));
        when(parkingFactory.getParkingSpot(new Vehicle("KN90H1234",SMALL,RED,BMW),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,1,new Vehicle("KN90H1234",SMALL,RED,BMW),System.currentTimeMillis(),NORMAL));
        when(parkingFactory.getParkingSpot(new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(0,2,new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA),System.currentTimeMillis(),NORMAL));
        parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL, WHITE,TOYOTA), NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1234",SMALL,RED,BMW), NORMAL);
        parkingLot.parkVehicle(new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA), NORMAL);
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
        when(parkingFactory.getParkingSpot(new Vehicle("AS90H1233",SMALL,WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,2,new Vehicle("AS90H1233",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),NORMAL));
        when(parkingFactory.getParkingSpot(new Vehicle("AS90H1230",SMALL,WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,3,new Vehicle("AS90H1230",SMALL,BLUE,TOYOTA),System.currentTimeMillis(),NORMAL));
        parkingLot.parkVehicle(new Vehicle("AS90H1233",SMALL,WHITE,TOYOTA), NORMAL);
        parkingLot.parkVehicle(new Vehicle("AS90H1230",SMALL,WHITE,TOYOTA), NORMAL);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("AS90H1230",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(1,3,new Vehicle("AS90H1230",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void given2LotsWithDifferentSize_whenFull_shouldThrowException() {
        try {
            when(parkingFactory.getParkingSpot(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA),slots,NORMAL))
                    .thenReturn(new ParkingSpot(1,2,new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA)));
            when(parkingFactory.getParkingSpot(new Vehicle("KN90H1233",SMALL,WHITE,TOYOTA),slots,NORMAL))
                    .thenReturn(new ParkingSpot(1,3,new Vehicle("KN90H1233",SMALL,BLUE,TOYOTA)));
            parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA), NORMAL);
            parkingLot.parkVehicle(new Vehicle("KN90H1233",SMALL,WHITE,TOYOTA), NORMAL);
            parkingLot.parkVehicle(new Vehicle("AS90H1233",SMALL,WHITE,TOYOTA), NORMAL);
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
        when(parkingFactory.getParkingSpot(new Vehicle("AS90H1239",SMALL,WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,2,new Vehicle("AS90H1239",SMALL,WHITE,TOYOTA)));
        boolean status = parkingLot.parkVehicle(new Vehicle("AS90H1239",SMALL,WHITE,TOYOTA), NORMAL);
        assertTrue(status);
    }

    @Test
    public void givenParkingLotWith2Slots_when3VehiclesParked_shouldReturnOccupiedSize() {
        int size = parkingLot.getParkingLotOccupiedSize();
        assertEquals(3,size);
    }

    @Test
    public void givenParkingLot_whenFull_shouldReturnClosed() {
        when(parkingFactory.getParkingSpot(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,2,new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA)));
        when(parkingFactory.getParkingSpot(new Vehicle("KN90H1233",SMALL,WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,3,new Vehicle("KN90H1233",SMALL,BLUE,TOYOTA)));
        parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA), NORMAL);
        parkingLot.parkVehicle(new Vehicle("KN90H1233",SMALL,WHITE,TOYOTA), NORMAL);
        ParkingLot.ParkingLotStatus status = parkingLot.getParkingLotStatus();
        assertEquals(CLOSED,status);
    }

    @Test
    public void givenParkingLot_whenNotFull_shouldReturnOpen() {
        when(parkingFactory.getParkingSpot(new Vehicle("AP10K0981",SMALL,RED,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,2,new Vehicle("AP10K0981",SMALL,RED,TOYOTA)));
        parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,RED,TOYOTA), NORMAL);
        ParkingLot.ParkingLotStatus status = parkingLot.getParkingLotStatus();
        assertEquals(OPEN,status);
    }

    @Test
    public void givenVehicleToPark_whenIfPresent_shouldThrowException() {
        try {
            parkingLot.parkVehicle(new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA), NORMAL);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.VEHICLE_EXISTS,e.type);
        }
    }

    @Test
    public void givenParkingLotWith2Slots_whenVehicleUnparkedAndOtherVehicleParked_shouldReturnResults() {
        parkingLot.unparkVehicle(new Vehicle("KN90H1234",SMALL,WHITE,BMW));
        when(parkingFactory.getParkingSpot(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,1,new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),NORMAL));
        parkingLot.parkVehicle(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA), NORMAL);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(1,1,new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
    }

    @Test
    public void givenParkingLotWith2Slots_whenHandicapedDriverVehicleParked_shouldReturnResults() {
        parkingLot.unparkVehicle(new Vehicle("AP10K0987",SMALL, WHITE,TOYOTA));
        when(parkingFactory.getParkingSpot(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA),slots,HANDICAPED))
            .thenReturn(new ParkingSpot(0,1,new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),NORMAL));
        parkingLot.parkVehicle(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA), HANDICAPED);
        ParkingSpot vehicle4Details = parkingLot.getParkingSpot(new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA));
        ParkingSpot expectedVehicle4Details = new ParkingSpot(0,1,new Vehicle("WA0K1230",SMALL,WHITE,TOYOTA));
        assertEquals(expectedVehicle4Details,vehicle4Details);
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
    public void givenLargeVehicleToPark_whenSpaceNotAvailable_shouldThrowException() {
        try {
            when(parkingFactory.getParkingSpot(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA),slots,NORMAL))
                .thenReturn(new ParkingSpot(1,2,new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA)));
            parkingLot.parkVehicle(new Vehicle("AP10K0981",SMALL,WHITE,TOYOTA), NORMAL);
            parkingLot.parkVehicle(new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA), NORMAL);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_SIZE_IS_LOW,e.type);
        }
    }

    @Test
    public void givenSmallAndLargeVehicles_whenLargeVehicleUnparked_shouldReturnResults() {
        when(parkingFactory.getParkingSpot(new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA),slots,NORMAL))
            .thenReturn(new ParkingSpot(1,2,new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA)));
        parkingLot.parkVehicle(new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA), NORMAL);
        int size = parkingLot.getParkingLotOccupiedSize();
        parkingLot.unparkVehicle(new Vehicle("KN90H1233",LARGE,WHITE,TOYOTA));
        int updatedSize = parkingLot.getParkingLotOccupiedSize();
        assertEquals(size-2,updatedSize);
    }

    @Test
    public void givenVehiclesToPark_whenParked_shouldReturnWhiteColorVehiclesParkingSpot() {
        List<ParkingSpot> whiteColorVehicles = parkingLot.getVehiclesDataByColor(WHITE);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA)));
        assertEquals(expectedList,whiteColorVehicles);
    }

    @Test
    public void givenVehiclesToPark_whenFetchForBlueToyota_shouldReturnResults() {
        List<ParkingSpot> buleToyotaVehicles = parkingLot.getVehiclesDataByColorAndBrand(BLUE,TOYOTA);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(0,2,"AS90H1234"));
        assertEquals(expectedList,buleToyotaVehicles);
    }

    @Test
    public void givenVehiclesToPark_whenFetchForBMW_shouldReturnResults() {
        List<ParkingSpot> buleToyotaVehicles = parkingLot.getVehiclesDataByBrand(BMW);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(1,1,new Vehicle("KN90H1234",SMALL,RED,BMW)));
        assertEquals(expectedList,buleToyotaVehicles);
    }

    @Test
    public void givenVehiclesToPark_whenFetchVehiclesParkedInLast30Minutes_shouldReturnResults() {
        List<ParkingSpot> vehiclesParkedInLast30Minutes = parkingLot.getVehiclesDataInLastGivenMinutes(30);
        List<ParkingSpot> expectedList = new ArrayList<>();
        expectedList.add(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA)));
        expectedList.add(new ParkingSpot(0,2,new Vehicle("AS90H1234",SMALL,BLUE,TOYOTA)));
        expectedList.add(new ParkingSpot(1,1,new Vehicle("KN90H1234",SMALL,RED,BMW)));
        assertEquals(expectedList,vehiclesParkedInLast30Minutes);
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
