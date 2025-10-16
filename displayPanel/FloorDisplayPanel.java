package displayPanel;

import parkingFloor.ParkingFloor;

public class FloorDisplayPanel extends Display<ParkingFloor>{
    public FloorDisplayPanel(ParkingFloor details) {
        super(details);
    }
    @Override
    public void showDetails() {
        System.out.println("---------------------------");
        System.out.println("Parking Floor Details:");
        System.out.println("Floor ID: " + details.getFloorId());
        System.out.println("Total Spots: " +details.getTotalSpots());
        System.out.println("Available Spots: " + details.getAvailableSpots());
        System.out.println("---------------------------");
    }
}
