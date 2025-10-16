package displayPanel;

import parkingLot.ParkingLot;

public class ParkingLotDisplayPanel extends Display<ParkingLot>{
    public ParkingLotDisplayPanel(ParkingLot details) {
        super(details);
    }

    @Override
    public void showDetails() {
        System.out.println("---------------------------");
        System.out.println("Parking lot Details");
        System.out.println("Number of Floors: " + details.getParkingFloorList().size());
        System.out.println("Number of Entry Points: " + 1);
        System.out.println("Number of Exit Points: " + 1);
        System.out.println("---------------------------");
    }
}