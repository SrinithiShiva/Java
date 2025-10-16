package parkingLot;

import java.util.HashMap;
import allocationStrategy.AllocationStrategy;
import feeManagement.Ticket;
import feeManagement.calculator.FeeCalculator;
import vehicle.Vehicle;

public class EntryExitPanel {
    private static HashMap<String, Ticket> ticketMap = new HashMap<>();
    public Ticket parkVehicle(Vehicle vehicle, AllocationStrategy allocationStrategy, ParkingLot parkingLot)
    {
        Ticket ticket = allocationStrategy.allocateSlot(vehicle,parkingLot);
        if(ticket!=null)
        {
            ticketMap.put(ticket.getTicketId(),ticket);
        }
        else
        {
            System.out.println("No Spot Available");
        }
        return ticket;
    }
    public Ticket retrieveLostTicket(String ticketId)
    {
        if(!ticketMap.containsKey(ticketId))
        {
            System.out.println("Invalid Ticket ID");
            return null;
        }
        return ticketMap.get(ticketId);
    }
    public void unParkVehicle(Vehicle vehicle, Ticket ticket, AllocationStrategy allocationStrategy, FeeCalculator feeCalculator)
    {
        if(!ticketMap.containsKey(ticket.getTicketId()))
        {
            System.out.println("Invalid Ticket ID");
            return;
        }
        if(vehicle.getVehicleNumber() != ticket.getVehicle().getVehicleNumber())
        {
            System.out.println("Vehicle Number does not match with Ticket");
            return;
        }
        allocationStrategy.deallocateSlot(ticket);
        feeCalculator.calculateFee(ticket);
        ticket.getTicketDetailsDisplayPanel().showDetails();
        ticketMap.remove(ticket.getTicketId());
    }
}
