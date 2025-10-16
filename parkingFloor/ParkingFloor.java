package parkingFloor;

import java.util.ArrayList;
import java.util.HashMap;
import displayPanel.Display;
import displayPanel.FloorDisplayPanel;
import parkingLot.ParkingLot;
import parkingSpot.ParkingSpot;

public class ParkingFloor {
    private String floorId;
    private ArrayList<ParkingSpot> parkingSpots; // true if available, false if occupied
    private ParkingLot parkingLot; // @Note: This attribute is used to know which parking lot this floor belongs to.(back reference)
    private Boolean isAvailable;
    private FloorDisplayPanel floorDisplayPanel;
    public ParkingFloor(final String floorId, final ArrayList<ParkingSpot> parkingSpots,ParkingLot parkingLot) {
        this.floorId = floorId;
        this.parkingSpots = parkingSpots;
        this.isAvailable = true;
        this.parkingLot=parkingLot;
        floorDisplayPanel = new FloorDisplayPanel(this);
    }
    public ParkingFloor(final String floorId,ParkingLot parkingLot) {
        this(floorId,new ArrayList<>(),parkingLot);
    }
    public String getFloorId() {
        return this.floorId;
    }
    public ArrayList<ParkingSpot> getParkingSpots() {
        return this.parkingSpots;
    }
    public Boolean getAvailable() {
        return this.isAvailable;
    }
    public void setAvailable(final Boolean available) {
        this.isAvailable = available;
    }
    public void addOrUpdateParkingSpot(final ParkingSpot parkingSpot) {
        this.parkingSpots.add(parkingSpot);
    }
    public int getTotalSpots() {
        return parkingSpots.size();
    }
    public int getAvailableSpots() {
        return (int) parkingSpots.stream().filter(parkingSpot -> parkingSpot.isAvailable()).count();
    }
    public Display<ParkingFloor> getParkingFloorDisplayPanel() {
        return this.floorDisplayPanel;
    }
}
