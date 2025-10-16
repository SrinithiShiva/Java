package allocationStrategy;

import feeManagement.Ticket;
import parkingLot.ParkingLot;
import vehicle.Vehicle;

public interface AllocationStrategy {
    Ticket allocateSlot(Vehicle vehicle, ParkingLot parkingLot);
    void deallocateSlot(Ticket ticket);
}
