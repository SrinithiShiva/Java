package vehicle;

import displayPanel.Display;
import displayPanel.VehicleDetailsDisplayPanel;

public class Vehicle {
    private String vehicleNumber;
    private String vehicleModel;
    private VehicleType vehicleType;
    private String vehicleOwner;
    private String phoneNumber;
    private SpotSize suitableSpotSize;
    private VehiclePriority priority;
    private VehicleDetailsDisplayPanel vehicleDetailsDisplayPanel;

    private Vehicle(VehicleBuilder builder) {
        this.vehicleNumber = builder.vehicleNumber;
        this.vehicleModel = builder.vehicleModel;
        this.vehicleType = builder.vehicleType;
        this.vehicleOwner = builder.vehicleOwner;
        this.phoneNumber = builder.phoneNumber;
        this.suitableSpotSize = builder.suitableSpotSize;
        this.priority = builder.priority;
        this.vehicleDetailsDisplayPanel=new VehicleDetailsDisplayPanel(this);
    }
    public String getVehicleNumber() {
        return this.vehicleNumber;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public VehicleType getVehicleType() {
        return this.vehicleType;
    }

    public String getVehicleOwner() {
        return this.vehicleOwner;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Display<Vehicle> getVehicleDetailsDisplayPanel() {
        return this.vehicleDetailsDisplayPanel;
    }
    public SpotSize getSuitableSpotSize() {
        return this.suitableSpotSize;
    }
    public VehiclePriority getPriority() {
        return this.priority;
    }

    public static class VehicleBuilder {
        private String vehicleNumber;
        private String vehicleModel;
        private VehicleType vehicleType;
        private SpotSize suitableSpotSize = SpotSize.SMALL;
        private VehiclePriority priority = VehiclePriority.LOW ;
        private String vehicleOwner;
        private String phoneNumber;

        public VehicleBuilder setVehicleNumber(final String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
            return this;
        }

        public VehicleBuilder setVehicleModel(final String vehicleModel) {
            this.vehicleModel = vehicleModel;
            return this;
        }

        public VehicleBuilder setVehicleType(final VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public VehicleBuilder setVehicleOwner(final String vehicleOwner) {
            this.vehicleOwner = vehicleOwner;
            return this;
        }

        public VehicleBuilder setPhoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public  VehicleBuilder setPriority(final VehiclePriority priority) {
            this.priority = priority;
            return this;
        }
        public VehicleBuilder setSuitableSpotSize(final SpotSize suitableSpotSize) {
            this.suitableSpotSize = suitableSpotSize;
            return this;
        }
        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}
