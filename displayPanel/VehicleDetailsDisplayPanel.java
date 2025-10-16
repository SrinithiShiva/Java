package displayPanel;

import vehicle.Vehicle;

public class VehicleDetailsDisplayPanel extends Display<Vehicle>{
    public VehicleDetailsDisplayPanel(Vehicle vehicle) {
        super(vehicle);
    }
    @Override
    public void showDetails() {
        System.out.println("---------------------------");
        System.out.println("Vehicle Details:");
        System.out.println("Vehicle Number: " + details.getVehicleNumber());
        System.out.println("Vehicle Model: " + details.getVehicleModel());
        System.out.println("Vehicle Type: " + details.getVehicleType());
        System.out.println("Vehicle Owner: " + details.getVehicleOwner());
        System.out.println("Phone Number: " + details.getPhoneNumber());
        System.out.println("---------------------------");
    }
}
