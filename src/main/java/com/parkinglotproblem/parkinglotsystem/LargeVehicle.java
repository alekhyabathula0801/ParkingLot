package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.exception.ParkingLotException;
import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Comparator;
import java.util.List;

public class LargeVehicle {

    public boolean parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        ParkingSlot parkingSlot = parkingSlots.stream()
                                              .filter(ParkingSlot::isOpen)
                                              .filter(parkingSlot1 -> parkingSlot1.checkVehicleCanParkInSlot(vehicle))
                                              .min(Comparator.comparing(ParkingSlot::getOccupiedSize))
                                              .get();
        int slotNumber = parkingSlots.indexOf(parkingSlot);
        for(int index =0;index<parkingSlot.getUnoccupiedSpots().size()-1;index++) {
            if (parkingSlot.getUnoccupiedSpots().get(index)+1 != parkingSlot.getUnoccupiedSpots().get(index + 1)) {
                if (parkingSlot.parkingSlotData.get(parkingSlot.getUnoccupiedSpots().get(index)+1)
                                                               .driverType.equals(ParkingLot.DriverType.NORMAL)) {
                    relocateNormalDriverVehicle(parkingSlot, index);
                    return parkLargeVehicle(slotNumber, parkingSlot.getUnoccupiedSpots().get(index), vehicle, parkingSlots);
                }
            } else {
                return parkLargeVehicle(slotNumber, parkingSlot.getUnoccupiedSpots().get(index), vehicle, parkingSlots);
            }
        }
        throw new ParkingLotException("Parking Slot is full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

    public void relocateNormalDriverVehicle(ParkingSlot parkingSlot,int index) {
        parkingSlot.parkingSlotData.put(parkingSlot.getUnoccupiedSpots().get(index+1),
                                        parkingSlot.parkingSlotData.get(parkingSlot.getUnoccupiedSpots().get(index)+1));
        parkingSlot.parkingSlotData.remove(parkingSlot.getUnoccupiedSpots().get(index));
    }

    public boolean parkLargeVehicle(int slotNumber,int spotNumber, Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        return parkingSlots.get(slotNumber).parkVehicle(new ParkingSpot(slotNumber,spotNumber,vehicle,
                                                        System.currentTimeMillis(),ParkingLot.DriverType.NORMAL));
    }

}
