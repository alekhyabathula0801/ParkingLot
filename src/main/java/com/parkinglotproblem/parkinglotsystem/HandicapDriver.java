package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.DriverType.HANDICAPED;

public class HandicapDriver {

    public ParkingSpot parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        for(ParkingSlot parkingSlot: parkingSlots) {
            for (int position=1; position<=parkingSlot.capacity; position++) {
                if(parkingSlot.parkingSlotData.containsKey(position)) {
                    if(parkingSlot.parkingSlotData.get(position).driverType.equals(ParkingLot.DriverType.NORMAL)) {
                        relocateNormalDriverVehicle(parkingSlots,position,parkingSlot);
                        parkingSlot.unparkVehicle(parkingSlot.parkingSlotData.get(position).vehicle);
                        return parkHandicapDriverVehicle(parkingSlots.indexOf(parkingSlot),position,parkingSlot,vehicle);
                    }
                } else
                    return parkHandicapDriverVehicle(parkingSlots.indexOf(parkingSlot),position,parkingSlot,vehicle);
            }
        }
        throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

    public void relocateNormalDriverVehicle(List<ParkingSlot> parkingSlots, int position, ParkingSlot parkingSlot) {
        ParkingSpot parkingSpot = new NormalDriver().parkVehicle(parkingSlot.parkingSlotData.get(position).vehicle,parkingSlots);
        parkingSlot.parkingSlotData.get(position).setSpotNumber(parkingSpot.spotNumber);
        parkingSlot.parkingSlotData.get(position).setSlotNumber(parkingSpot.slotNumber);
    }

    public ParkingSpot parkHandicapDriverVehicle(int slotNumber, int position, ParkingSlot parkingSlot, Vehicle vehicle) {
        ParkingSpot parkingSpot = new ParkingSpot(slotNumber,position,vehicle,System.currentTimeMillis(),HANDICAPED);
        parkingSlot.parkVehicle(parkingSpot);
        return parkingSpot;
    }

}
