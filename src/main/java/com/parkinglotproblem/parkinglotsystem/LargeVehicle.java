package com.parkinglotproblem.parkinglotsystem;

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
        if (parkingSlot.getUnoccupiedSpots().get(0)+1 != parkingSlot.getUnoccupiedSpots().get(1)) {
            relocateVehicle(parkingSlot,parkingSlot.getUnoccupiedSpots().get(1)-1);
            return parkLargeVehicle(slotNumber,parkingSlot.getUnoccupiedSpots().get(0), vehicle, parkingSlots);
        } else
            return parkLargeVehicle(slotNumber, parkingSlot.getUnoccupiedSpots().get(0), vehicle, parkingSlots);
        }

    public void relocateVehicle(ParkingSlot parkingSlot, int position) {
        parkingSlot.parkingSlotData.put(parkingSlot.getUnoccupiedSpots().get(0),parkingSlot.parkingSlotData.get(position));
        parkingSlot.parkingSlotData.remove(position);
    }

    public boolean parkLargeVehicle(int slotNumber,int spotNumber, Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        return parkingSlots.get(slotNumber).parkVehicle(new ParkingSpot(slotNumber,spotNumber,vehicle,
                                                        System.currentTimeMillis(),ParkingLot.DriverType.NORMAL));
    }

}
