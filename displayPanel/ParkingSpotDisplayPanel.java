package displayPanel;

import parkingSpot.ParkingSpot;

public class ParkingSpotDisplayPanel extends Display<ParkingSpot> {
    public ParkingSpotDisplayPanel(ParkingSpot details) {
        super(details);
    }
    @Override
    public void showDetails() {
        System.out.println("---------------------------");
        System.out.println("Parking Spot Details:");
        System.out.println("Spot ID: " + details.getParkingSpotId());
        System.out.println("Spot Size: " + details.getSpotSize());
        System.out.println("Is Available: " + details.isAvailable());
        System.out.println("---------------------------");
    }
}
