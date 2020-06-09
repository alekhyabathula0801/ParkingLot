package com.parkinglotproblem.parkingservice;

import com.parkinglotproblem.parkingsystem.Customer;
import com.parkinglotproblem.parkingsystem.ParkingRepository;
import com.parkinglotproblem.parkingsystem.VehicleType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    ParkingRepository parkingRepository;

    @InjectMocks
    ParkingService parkingService;

    @Test
    public void givenCarToPark_whenAdded_shouldIncreaseSize() {
        when(parkingRepository.getSize()).thenReturn(1);
        parkingService.parkVehicle("TS 09 K 1234",new Customer("Sweety",VehicleType.CAR));
        int size = parkingService.getParkingLotSize();
        assertEquals(1,size);
    }

    @Test
    public void givenCarToUnPark_whenRemoved_shouldDecreaseSize() {
        when(parkingRepository.getSize()).thenReturn(1);
        parkingService.parkVehicle("TS 09 K 1234",new Customer("Sweety",VehicleType.CAR));
        parkingService.parkVehicle("TS 09 K 4321",new Customer("Arjun",VehicleType.CAR));
        parkingService.unparkVehicle("TS 09 K 1234");
        int size = parkingService.getParkingLotSize();
        assertEquals(1,size);
    }

}
