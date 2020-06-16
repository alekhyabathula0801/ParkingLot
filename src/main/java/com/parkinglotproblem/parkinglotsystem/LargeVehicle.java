package com.parkinglotproblem.parkinglotsystem;

import com.parkinglotproblem.vehicle.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LargeVehicle {

    public ParkingSpot getParkingSpot(Vehicle vehicle, List<ParkingSlot> parkingSlots, ParkingLot.DriverType driverType) {
        List<ParkingSlot> sortedParkingSlots = parkingSlots.stream()
                                                           .filter(ParkingSlot::isOpen)
                                                           .sorted(Comparator.comparing(ParkingSlot::getOccupiedSize))
                                                           .collect(Collectors.toList());
        ParkingSlot parkingSlot1 = sortedParkingSlots.get(0);

        for(ParkingSlot sortedParkingSlot : sortedParkingSlots) {
            int index = parkingSlots.indexOf(sortedParkingSlot);
            if (index == 0 | index == parkingSlots.size() - 1) {
                parkingSlot1 = sortedParkingSlot;
                break;
            } else if (parkingSlots.get(index).parkingSlotData.get(1) == null && parkingSlots.get(index - 1).parkingSlotData.get(1) == null) {
                parkingSlot1 = sortedParkingSlot;
                break;
            } else if (parkingSlots.get(index).parkingSlotData.get(1) == null && parkingSlots.get(index + 1).parkingSlotData.get(1) == null) {
                parkingSlot1 = sortedParkingSlot;
                break;
            }
        }

        if(parkingSlot1.getUnoccupiedSpots().size() >= vehicle.vehicleSize.size) {
            if(parkingSlot1.getUnoccupiedSpots().get(0) + 1 != parkingSlot1.getUnoccupiedSpots().get(1))
                relocateVehicle(parkingSlot1, parkingSlot1, parkingSlots.indexOf(parkingSlot1),
                                parkingSlot1.getUnoccupiedSpots().get(0),parkingSlot1.getUnoccupiedSpots().get(1)-1);
        } else {
            if (parkingSlot1.parkingSlotData.get(parkingSlot1.getUnoccupiedSpots().get(0) + 1) != null) {
                sortedParkingSlots.remove(parkingSlot1);
                relocateVehicle(parkingSlot1,sortedParkingSlots.get(0),parkingSlots.indexOf(sortedParkingSlots.get(0)),
                                sortedParkingSlots.get(0).getUnoccupiedSpots().get(0),parkingSlot1.getUnoccupiedSpots().get(0)+1);
            } else {
                sortedParkingSlots.remove(parkingSlot1);
                relocateVehicle(parkingSlot1,sortedParkingSlots.get(0),parkingSlots.indexOf(sortedParkingSlots.get(0)),
                                sortedParkingSlots.get(0).getUnoccupiedSpots().get(0),parkingSlot1.getUnoccupiedSpots().get(0)-1);
            }
        }
        return new ParkingSpot(parkingSlots.indexOf(parkingSlot1),parkingSlot1.getUnoccupiedSpots().get(0),vehicle,
                               System.currentTimeMillis(),driverType);
    }

    public void relocateVehicle(ParkingSlot parkingSlot1, ParkingSlot parkingSlot2, int slotNumber, int spotNumberToSet,int spotNumberOfVehicle) {
        parkingSlot1.parkingSlotData.get(spotNumberOfVehicle).setSlotNumber(slotNumber);
        parkingSlot1.parkingSlotData.get(spotNumberOfVehicle).setSpotNumber(spotNumberToSet);
        parkingSlot2.parkingSlotData.put(parkingSlot2.getUnoccupiedSpots().get(0),parkingSlot1.parkingSlotData.get(spotNumberOfVehicle));
        parkingSlot1.parkingSlotData.remove(spotNumberOfVehicle);
    }

}
