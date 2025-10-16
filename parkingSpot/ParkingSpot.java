package parkingSpot;

import java.util.Random;
import java.util.function.Supplier;

import displayPanel.Display;
import displayPanel.ParkingSpotDisplayPanel;
import parkingFloor.ParkingFloor;
import vehicle.SpotSize;
import vehicle.Vehicle;
import vehicle.VehiclePriority;

public class ParkingSpot {
    private String parkingSpotId;
    private Vehicle vehicle;
    private SpotSize spotSize;
    private VehiclePriority priority;
    private boolean isAvailable;
    private ParkingFloor parkingFloor;      //@Note:This attribute is used to know which floor this spot belongs to.(back reference)
    private ParkingSpotDisplayPanel parkingSpotDisplayPanel;


    private ParkingSpot(ParkingSpotBuilder builder) {
        this.parkingSpotId=generateParkingSpotId.get();
        this.spotSize=builder.spotSize;
        this.priority=builder.priority;
        this.isAvailable=true;
        this.parkingFloor=builder.parkingFloor;
        this.parkingSpotDisplayPanel=new ParkingSpotDisplayPanel(this);
    }
    private Supplier<String> generateParkingSpotId = ()->{
        Random random = new Random();
        return "SPOT".concat(String.valueOf(1000 + random.nextInt(9000)));
    };
    public String getParkingSpotId() {
        return this.parkingSpotId;
    }
    public Vehicle getVehicle() {
        return this.vehicle;
    }
    public void setVehicle(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public Display<ParkingSpot> getParkingSpotDisplayPanel(){
        return this.parkingSpotDisplayPanel;
    }
    public boolean isAvailable() {
        return this.isAvailable;
    }
    public void setAvailable(final boolean available) {
        this.isAvailable = available;
    }
    public SpotSize getSpotSize() {
        return this.spotSize;
    }
    public VehiclePriority getPriority() {
        return this.priority;
    }
    public ParkingFloor getParkingFloor() {
        return this.parkingFloor;
    }
    public static class ParkingSpotBuilder{
        private SpotSize spotSize=SpotSize.SMALL;
        private VehiclePriority priority=VehiclePriority.LOW;
        private ParkingFloor parkingFloor;
        public ParkingSpotBuilder(ParkingFloor parkingFloor){
            this.parkingFloor=parkingFloor;
        }
        public ParkingSpotBuilder setSpotSize(final SpotSize spotSize) {
            this.spotSize = spotSize;
            return this;
        }
        public ParkingSpotBuilder setPriority(final VehiclePriority priority) {
            this.priority = priority;
            return this;
        }
        public ParkingSpot build(){
            return new ParkingSpot(this);
        }
    }
}
