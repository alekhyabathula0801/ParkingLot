package com.parkinglotproblem.parkinglotsystem;

import java.util.HashMap;
import java.util.function.Predicate;

import static com.parkinglotproblem.vehicle.Vehicle.VehicleBrand.*;
import static java.awt.Color.*;

public class ParkingSpotPredicators {

    static HashMap<ParkingLot.FetchBy, Predicate<ParkingSpot>> predicate = new HashMap<>();

    static  {
        predicate.put(ParkingLot.FetchBy.WHITE,parkingSpot -> parkingSpot.vehicle.vehicleColor== WHITE);
        predicate.put(ParkingLot.FetchBy.BLUE_TOYATO,parkingSpot -> parkingSpot.vehicle.vehicleColor== BLUE
                        && parkingSpot.vehicle.brand == TOYOTA);
        predicate.put(ParkingLot.FetchBy.BMW,parkingSpot -> parkingSpot.vehicle.brand == BMW);
        predicate.put(ParkingLot.FetchBy.HANDICAP_DRIVER_IN_SLOT1, parkingSpot -> parkingSpot.driverType == ParkingLot.DriverType.HANDICAPED
                      && parkingSpot.slotNumber == 1);
        predicate.put(ParkingLot.FetchBy.THIRTY_MINUTES,parkingSpot -> System.currentTimeMillis()/60000.0-parkingSpot.inTime/60000.0 < 30.0);

    }

    public Predicate<ParkingSpot> getPredicate(ParkingLot.FetchBy fetchBy) {
        return predicate.get(fetchBy);
    }

}
