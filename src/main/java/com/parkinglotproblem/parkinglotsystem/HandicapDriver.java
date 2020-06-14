package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.DriverType.HANDICAPED;

public class HandicapDriver {

    public boolean parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        for(ParkingSlot parkingSlot: parkingSlots) {
            for(int position=1; position<=parkingSlot.capacity; position++) {
                if(parkingSlot.parkingSlotData.containsKey(position)) {
                    if(parkingSlot.parkingSlotData.get(position).driverType.equals(ParkingLot.DriverType.NORMAL)) {
                        relocateNormalDriverVehicle(parkingSlots,position,parkingSlot);
                        return parkHandicapDriverVehicle(parkingSlots.indexOf(parkingSlot),position,parkingSlot,vehicle);
                    }
                } else
                    return parkHandicapDriverVehicle(parkingSlots.indexOf(parkingSlot),position,parkingSlot,vehicle);
            }
        }
        throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

    public void relocateNormalDriverVehicle(List<ParkingSlot> parkingSlots, int position, ParkingSlot parkingSlot) {
        new NormalDriver().parkVehicle(parkingSlot.parkingSlotData.get(position).vehicle,parkingSlots);
        parkingSlot.unparkVehicle(parkingSlot.parkingSlotData.get(position).vehicle);
    }

    public boolean parkHandicapDriverVehicle(int slotNumber, int position, ParkingSlot parkingSlot, Vehicle vehicle) {
        return parkingSlot.parkVehicle(new ParkingSpot(slotNumber,position,vehicle,System.currentTimeMillis(),HANDICAPED));
    }

}
