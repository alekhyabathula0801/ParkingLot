package com.parkinglotproblem.parkingservice;

import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;
import com.parkinglotproblem.parkingsystem.ParkingRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static com.parkinglotproblem.parkingservice.ParkingService.ParkingLotStatus.CLOSED;
import static com.parkinglotproblem.parkingservice.ParkingService.ParkingLotStatus.OPEN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    ParkingRepository parkingRepository;

    @Mock
    ParkingLotOwner parkingLotOwner;

    @InjectMocks
    ParkingService parkingService;

    @Before
    public void setUp() throws Exception {
        parkingRepository.setCapacity(5);
    }

    @Test
    public void givenCarToPark_whenAdded_shouldIncreaseSize() {
        when(parkingRepository.getOccupiedSize()).thenReturn(1);
        when(parkingRepository.parkVehicle(new Vehicle("TS 09 K 1234"),1)).thenReturn(true);
        when(parkingLotOwner.assignSlotNumber(parkingRepository.getOccupiedSlots(),parkingRepository.capacity)).thenReturn(1);
        parkingService.parkVehicle(new Vehicle("TS 09 K 1234"));
        int size = parkingService.getParkingLotOccupiedSize();
        assertEquals(1,size);
    }

    @Test
    public void givenCarToUnPark_whenRemoved_shouldDecreaseSize() {
        when(parkingRepository.getOccupiedSize()).thenReturn(0);
        when(parkingRepository.parkVehicle(new Vehicle("TS 09 K 1234"),1)).thenReturn(true);
        when(parkingLotOwner.assignSlotNumber(parkingRepository.getOccupiedSlots(),parkingRepository.capacity)).thenReturn(1);
        when(parkingRepository.unparkVehicle(new Vehicle("TS 09 K 1234"))).thenReturn(true);
        parkingService.parkVehicle(new Vehicle("TS 09 K 1234"));
        parkingService.unparkVehicle(new Vehicle("TS 09 K 1234"));
        int size = parkingService.getParkingLotOccupiedSize();
        assertEquals(0,size);
    }

    @Test
    public void givenCarToPark_whenFull_shouldReturnClosed() {
        when(parkingRepository.isFull()).thenReturn(true);
        ParkingService.ParkingLotStatus status = parkingService.getParkingLotStatus();
        assertEquals(CLOSED,status);
    }

    @Test
    public void givenCarToPark_whenNotFull_shouldReturnOpen() {
        when(parkingRepository.isFull()).thenReturn(false);
        ParkingService.ParkingLotStatus status = parkingService.getParkingLotStatus();
        assertEquals(OPEN,status);
    }

    @Test
    public void givenCarToPark_whenParked_shouldReturnSlotNumber() {
        when(parkingRepository.getSlotNumber(new Vehicle("TS09K4321"))).thenReturn(1);
        when(parkingRepository.parkVehicle(new Vehicle("TS09K4321"),1)).thenReturn(true);
        when(parkingLotOwner.assignSlotNumber(parkingRepository.getOccupiedSlots(),parkingRepository.capacity)).thenReturn(1);
        parkingService.parkVehicle(new Vehicle("TS09K4321"));
        int slotNumber = parkingService.getSlotNumber(new Vehicle("TS09K4321"));
        assertEquals(1,slotNumber);
    }

}
