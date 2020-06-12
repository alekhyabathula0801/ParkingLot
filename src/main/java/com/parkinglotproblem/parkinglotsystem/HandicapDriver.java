package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.parkinglotowner.ParkingLotOwner;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.List;

import static com.parkinglotproblem.parkinglotsystem.ParkingLot.DriverType.HANDICAPED;

public class HandicapDriver {

    public ParkingSpot parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        for(ParkingSlot parkingSlot: parkingSlots) {
            for (int position=1; position<=parkingSlot.capacity; position++) {
                if(parkingSlot.parkingData.containsKey(position)) {
                    if(parkingSlot.parkingData.get(position).driverType.equals(ParkingLot.DriverType.NORMAL)) {
                        relocateNormalDriverVehicle(parkingSlots,position,parkingSlot);
                        return parkHandicapDriverVehicle(parkingSlots,position,parkingSlot,vehicle);
                    }
                } else
                    return parkHandicapDriverVehicle(parkingSlots,position,parkingSlot,vehicle);
            }
        }
        throw new ParkingLotException("Parking Lot is Full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

    public void relocateNormalDriverVehicle(List<ParkingSlot> parkingSlots, int position, ParkingSlot parkingSlot) {
        int slotNumber=0;
        for (ParkingSlot slot : parkingSlots) {
            int  index = new ParkingLotOwner().assignSlotNumber(slot.getOccupiedSlots(), slot.capacity);
            if(index > -1) {
                parkingSlot.parkingData.get(position).setPosition(index);
                parkingSlot.parkingData.get(position).setSlotNumber(slotNumber);
            }
            slotNumber++;
        }
    }

    public ParkingSpot parkHandicapDriverVehicle(List<ParkingSlot> parkingSlots, int position, ParkingSlot parkingSlot, Vehicle vehicle) {
        int slotNumber = parkingSlots.indexOf(parkingSlot);
        ParkingSpot parkingSpot = new ParkingSpot(slotNumber,position,vehicle,System.currentTimeMillis(),HANDICAPED);
        parkingSlot.parkVehicle(parkingSpot);
        return parkingSpot;
    }

}
