package com.parkinglotproblem.airportmanagement;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class AirportSecurityTest {

    @Test
    public void givenParkingLotStatus_whenfull_shouldReturnTrue() {
        AirportSecurity airportSecurity = new AirportSecurity();
        boolean status = airportSecurity.getParkingLotStatus(true);
        assertTrue(status);
    }

}
