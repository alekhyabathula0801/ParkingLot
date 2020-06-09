package com.parkinglotproblem.airportmanagement;

import com.parkinglotproblem.parkingservice.ParkingService;
import com.parkinglotproblem.parkingsystem.ParkingLotException;

public class AirportSecurity {

    private ParkingService parkingService;

    public void getParkingLotSize() {
        ParkingService.ParkingLotStatus status = parkingService.getParkingLotStatus();
        if(status == ParkingService.ParkingLotStatus.CLOSED)
            throw new ParkingLotException("Redirect Airport Security Staff", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

}
