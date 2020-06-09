package com.parkinglotproblem.airportmanagement;

import com.parkinglotproblem.parkingservice.ParkingService;
import com.parkinglotproblem.parkingsystem.ParkingLotException;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AirportSecurityTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    ParkingService parkingService;

    @InjectMocks
    AirportSecurity airportSecurity;

    @Test
    public void givenParkingLotStatus_whenfull_shouldThrowException() {
        try {
            when(parkingService.getParkingLotStatus()).thenReturn(ParkingService.ParkingLotStatus.CLOSED);
            airportSecurity.getParkingLotSize();
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL,e.type);
        }
    }

}
