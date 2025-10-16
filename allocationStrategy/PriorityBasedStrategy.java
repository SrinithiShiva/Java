package allocationStrategy;

import java.time.LocalDateTime;

import feeManagement.Ticket;
import parkingFloor.ParkingFloor;
import parkingLot.ParkingLot;
import parkingSpot.ParkingSpot;
import vehicle.Vehicle;

public class PriorityBasedStrategy implements AllocationStrategy {
    @Override
    public Ticket allocateSlot(Vehicle vehicle, ParkingLot parkingLot) {
        // Implement priority-based allocation logic here
        Ticket ticket = null;
        main:
        for(ParkingFloor parkingFloor:parkingLot.getParkingFloorList()){
            if(!parkingFloor.getAvailable()) {
                continue;
            }
            for(ParkingSpot parkingSpot : parkingFloor.getParkingSpots()){
                if(parkingSpot.isAvailable() && parkingSpot.getPriority().equals(vehicle.getPriority())){
                    parkingSpot.setVehicle(vehicle);
                    parkingSpot.setAvailable(false);
                    ticket = new Ticket(LocalDateTime.now(),vehicle, parkingSpot);
                    break main;
                }
            }
            if(parkingFloor.getAvailableSpots()==0){
                parkingFloor.setAvailable(false);
            }
        }
        return ticket;
    }

    @Override
    public void deallocateSlot(final Ticket ticket) {
        ticket.setExitTime(LocalDateTime.now());
        ParkingSpot parkingSpot = ticket.getParkingSpot();
        parkingSpot.setAvailable(true);

        ParkingFloor parkingFloor = parkingSpot.getParkingFloor();
        parkingFloor.setAvailable(true);
    }
}
