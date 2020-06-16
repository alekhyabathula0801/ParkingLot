package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.DriverType.*;
import static com.parkinglotproblem.vehicle.Vehicle.VehicleBrand.*;
import static com.parkinglotproblem.vehicle.Vehicle.VehicleSize.*;
import static java.awt.Color.*;
import static org.junit.Assert.assertEquals;

public class ParkingFactoryTest {

    ParkingFactory parkingFactory;
    List<ParkingSlot> parkingSlots;
    @Before
    public void setUp() throws Exception {
        parkingFactory = new ParkingFactory();
        parkingSlots = new ArrayList<>();
    }

    @Test
    public void givenParkingSlotsAndVehicleDetails_whenNormalDriver_shouldReturnParkingSpot() {
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.get(0).parkVehicle(new ParkingSpot(0,1,new Vehicle("AP10K0987",
                SMALL,WHITE,TOYOTA),System.currentTimeMillis(),NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("AS09H1234",SMALL,BLACK,BMW),parkingSlots,
                                                                NORMAL);
        assertEquals(new ParkingSpot(1,1,new Vehicle("AS09H1234",SMALL,BLACK,BMW)),parkingSpot);
    }

    @Test
    public void givenParkingSlotsAndVehicleDetails_whenHandicapDriver_shouldReturnParkingSpot() {
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.get(0).parkVehicle(new ParkingSpot(0,1,new Vehicle("AP10K0987",
                                        SMALL,WHITE,TOYOTA),System.currentTimeMillis(),NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("AS09H1234", SMALL,BLACK, BMW),parkingSlots,
                                                                HANDICAPED);
        assertEquals(new ParkingSpot(0,1,new Vehicle("AS09H1234", SMALL,BLACK, BMW)),parkingSpot);
        assertEquals(new ParkingSpot(1,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),NORMAL),
                                     parkingSlots.get(1).parkingSlotData.get(1));
    }

    @Test
    public void givenHandicapDriverVehicleToPark_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.get(0).parkVehicle(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),
                                        System.currentTimeMillis(),NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA),parkingSlots,
                                                                HANDICAPED);
        assertEquals(new ParkingSpot(0,1,new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA)),parkingSpot);
    }

    @Test
    public void givenVehicleToPark_whenHandicapDriver_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.add(new ParkingSlot(3));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA),parkingSlots,
                                                                HANDICAPED);
        assertEquals(new ParkingSpot(0,1,new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA)),parkingSpot);
    }

    @Test
    public void givenVehicleToPark_whenHandicapDriversToPark_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.get(0).parkVehicle(new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),
                                        HANDICAPED));
        parkingSlots.get(1).parkVehicle(new ParkingSpot(0,1,new Vehicle("AP10K0999",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),
                                                        NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA),parkingSlots,
                                                                HANDICAPED);

        assertEquals(new ParkingSpot(0,2,new Vehicle("SK09A5678",SMALL,WHITE,TOYOTA)),parkingSpot);
    }

    @Test
    public void givenLargeAndSmallVehiclesToPark_whenParked_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(4));
        parkingSlots.get(0).parkingSlotData.put(1,new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),
                                                System.currentTimeMillis(), NORMAL));
        parkingSlots.get(0).parkingSlotData.put(3,new ParkingSpot(0,3,new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA),
                                                System.currentTimeMillis(), NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), parkingSlots
                                                                ,NORMAL);
        assertEquals(new ParkingSpot(0,3,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA)),parkingSpot);
        assertEquals(new ParkingSpot(0,2,new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),
                                     NORMAL),parkingSlots.get(0).parkingSlotData.get(2));
    }

    @Test
    public void givenLargeAndSmallVehicles_whenParked_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(4));
        parkingSlots.get(0).parkingSlotData.put(2,new ParkingSpot(0,2,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(),NORMAL));
        parkingSlots.get(0).parkingSlotData.put(3,new ParkingSpot(0,3,new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA),
                                                System.currentTimeMillis(),NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), parkingSlots,
                                                                NORMAL);
        assertEquals(new ParkingSpot(0,3,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA)),parkingSpot);
        assertEquals(new ParkingSpot(0,1,new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),
                                     NORMAL),parkingSlots.get(0).parkingSlotData.get(1));
    }

    @Test
    public void givenSmallVehicleToParkAndUnpark_whenLargeVehicleParked_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.get(0).parkingSlotData.put(1,new ParkingSpot(0,1,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        parkingSlots.get(1).parkingSlotData.put(1,new ParkingSpot(1,1,new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), parkingSlots,
                                                                NORMAL);
        assertEquals(new ParkingSpot(0,1,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA)),parkingSpot);
        assertEquals(new ParkingSpot(1,2,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),
                                     NORMAL),parkingSlots.get(1).parkingSlotData.get(2));
    }

    @Test
    public void givenVehiclesToParkAndUnpark_whenLargeVehicleParked_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.add(new ParkingSlot(2));
        parkingSlots.get(0).parkingSlotData.put(2,new ParkingSpot(0,2,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),
                                                System.currentTimeMillis(), NORMAL));
        parkingSlots.get(1).parkingSlotData.put(1,new ParkingSpot(1,1,new Vehicle("AS90H1234",SMALL,WHITE,TOYOTA),
                                                System.currentTimeMillis(), NORMAL));
        ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), parkingSlots,
                                                                NORMAL);
        assertEquals(new ParkingSpot(0,1,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA)),parkingSpot);
        assertEquals(new ParkingSpot(1,2,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),System.currentTimeMillis(),
                NORMAL),parkingSlots.get(1).parkingSlotData.get(2));
    }

    @Test
    public void givenVehiclesToParkAndUnpark_whenLargeVehicleParkedAccordingToManoeuvre_shouldReturnResults() {
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.add(new ParkingSlot(3));
        parkingSlots.get(0).parkingSlotData.put(1,new ParkingSpot(0,1,new Vehicle("AP10K0980",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        parkingSlots.get(0).parkingSlotData.put(2,new ParkingSpot(0,2,new Vehicle("AP10K0987",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        parkingSlots.get(0).parkingSlotData.put(3,new ParkingSpot(0,3,new Vehicle("NP10K0985",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        parkingSlots.get(1).parkingSlotData.put(1,new ParkingSpot(1,1,new Vehicle("AP10K0912",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        parkingSlots.get(2).parkingSlotData.put(3,new ParkingSpot(2,3,new Vehicle("AS90H1233",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        parkingSlots.get(3).parkingSlotData.put(2,new ParkingSpot(3,2,new Vehicle("AP10K0900",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
        parkingSlots.get(3).parkingSlotData.put(3,new ParkingSpot(3,3,new Vehicle("AS90H1211",SMALL,WHITE,TOYOTA),
                                                                  System.currentTimeMillis(), NORMAL));
       ParkingSpot parkingSpot = parkingFactory.getParkingSpot(new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA), parkingSlots,
                                                               NORMAL);
        assertEquals(new ParkingSpot(2,1,new Vehicle("AP10K0980",LARGE,WHITE,TOYOTA)),parkingSpot);
    }

}
