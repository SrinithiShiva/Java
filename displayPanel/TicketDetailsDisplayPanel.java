package displayPanel;

import feeManagement.Ticket;

public class TicketDetailsDisplayPanel extends Display<Ticket>{
    public TicketDetailsDisplayPanel(Ticket ticketDetails) {
        super(ticketDetails);
    }
    @Override
    public void showDetails() {
        System.out.println("---------------------------");
        System.out.println("Ticket Details:");
        System.out.println("Ticket ID: " + details.getTicketId());
        System.out.println("Vehicle Number: " + details.getVehicle().getVehicleNumber());
        System.out.println("Parking Spot ID: " + details.getParkingSpot().getParkingSpotId());
        System.out.println("Entry Time: " + details.getEntryTime());
        System.out.println("Exit Time: " + details.getExitTime());
        System.out.println("Total Fee: " + details.getFee());
        System.out.println("---------------------------");
    }
}
