package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.HashMap;
import java.util.function.Predicate;

public class ParkingSpotPredicators {

    static HashMap<ParkingLot.FetchBy, Predicate<ParkingSpot>> predicate = new HashMap<>();

    static  {
        predicate.put(ParkingLot.FetchBy.WHITE,parkingSpot -> parkingSpot.vehicle.vehicleColor== Vehicle.VehicleColor.WHITE);
        predicate.put(ParkingLot.FetchBy.BLUE_TOYATO,parkingSpot -> parkingSpot.vehicle.vehicleColor==
                      Vehicle.VehicleColor.BLUE && parkingSpot.vehicle.brand == "TOYOTA");
    }

    public Predicate<ParkingSpot> getPredicate(ParkingLot.FetchBy fetchBy) {
        return predicate.get(fetchBy);
    }

}
