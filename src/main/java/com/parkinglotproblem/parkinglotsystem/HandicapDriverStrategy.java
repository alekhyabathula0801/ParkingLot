package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.DriverType.HANDICAPED;

public class HandicapDriverStrategy {

    public ParkingSpot getParkingSpot(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        for(ParkingSlot parkingSlot: parkingSlots) {
            for(int position=1; position<=parkingSlot.capacity; position++) {
                if(parkingSlot.parkingSlotData.containsKey(position)) {
                    if(parkingSlot.parkingSlotData.get(position).driverType.equals(ParkingLot.DriverType.NORMAL)
                       && parkingSlot.parkingSlotData.get(position).vehicle.vehicleSize.equals(Vehicle.VehicleSize.SMALL)) {
                        relocateNormalDriverVehicle(parkingSlots,position,parkingSlot);
                        return getParkingSpotOfHandicapDriver(parkingSlots.indexOf(parkingSlot),position,vehicle);
                    }
                } else
                    return getParkingSpotOfHandicapDriver(parkingSlots.indexOf(parkingSlot),position,vehicle);
            }
        }
        throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

    public void relocateNormalDriverVehicle(List<ParkingSlot> parkingSlots, int position, ParkingSlot parkingSlot) {
        ParkingSpot parkingSpot =new NormalDriverStrategy().getParkingSpot(parkingSlot.parkingSlotData.get(position).vehicle,parkingSlots);
        parkingSlots.get(parkingSpot.slotNumber).parkVehicle(parkingSpot);
        parkingSlot.unparkVehicle(parkingSlot.parkingSlotData.get(position).vehicle);
    }

    public ParkingSpot getParkingSpotOfHandicapDriver(int slotNumber, int position, Vehicle vehicle) {
        return new ParkingSpot(slotNumber,position,vehicle,System.currentTimeMillis(),HANDICAPED);
    }

}
