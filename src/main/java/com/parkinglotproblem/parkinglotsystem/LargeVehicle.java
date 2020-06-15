package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LargeVehicle {

    public boolean parkVehicle(Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        List<ParkingSlot> sortedParkingSlots = parkingSlots.stream()
                                                           .filter(ParkingSlot::isOpen)
                                                           .sorted(Comparator.comparing(ParkingSlot::getOccupiedSize))
                                                           .collect(Collectors.toList());
        ParkingSlot parkingSlot1 = sortedParkingSlots.get(0);
        if(parkingSlot1.getUnoccupiedSpots().size() >= vehicle.vehicleSize.size) {
            if(parkingSlot1.getUnoccupiedSpots().get(0) + 1 != parkingSlot1.getUnoccupiedSpots().get(1))
                relocateVehicle(parkingSlot1, parkingSlot1, parkingSlots.indexOf(parkingSlot1),
                                parkingSlot1.getUnoccupiedSpots().get(0),parkingSlot1.getUnoccupiedSpots().get(1)-1);
        } else {
            if (parkingSlot1.parkingSlotData.get(parkingSlot1.getUnoccupiedSpots().get(0) + 1) != null)
                relocateVehicle(parkingSlot1,sortedParkingSlots.get(1),parkingSlots.indexOf(sortedParkingSlots.get(1)),
                                sortedParkingSlots.get(1).getUnoccupiedSpots().get(0),parkingSlot1.getUnoccupiedSpots().get(0)+1);
            else
                relocateVehicle(parkingSlot1,sortedParkingSlots.get(1),parkingSlots.indexOf(sortedParkingSlots.get(1)),
                                sortedParkingSlots.get(1).getUnoccupiedSpots().get(0),parkingSlot1.getUnoccupiedSpots().get(0)-1);
        }
        return parkLargeVehicle(parkingSlots.indexOf(parkingSlot1),parkingSlot1.getUnoccupiedSpots().get(0),vehicle,parkingSlots);
    }

    public void relocateVehicle(ParkingSlot parkingSlot1, ParkingSlot parkingSlot2, int slotNumber, int spotNumberToSet,int spotNumberOfVehicle) {
        parkingSlot1.parkingSlotData.get(spotNumberOfVehicle).setSlotNumber(slotNumber);
        parkingSlot1.parkingSlotData.get(spotNumberOfVehicle).setSpotNumber(spotNumberToSet);
        parkingSlot2.parkingSlotData.put(parkingSlot2.getUnoccupiedSpots().get(0),parkingSlot1.parkingSlotData.get(spotNumberOfVehicle));
        parkingSlot1.parkingSlotData.remove(spotNumberOfVehicle);
    }

    public boolean parkLargeVehicle(int slotNumber,int spotNumber, Vehicle vehicle, List<ParkingSlot> parkingSlots) {
        return parkingSlots.get(slotNumber).parkVehicle(new ParkingSpot(slotNumber,spotNumber,vehicle,
                                                        System.currentTimeMillis(),ParkingLot.DriverType.NORMAL));
    }

}
