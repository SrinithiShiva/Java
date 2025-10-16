import allocationStrategy.PriorityBasedStrategy;
import feeManagement.Ticket;
import feeManagement.calculator.VehicleTypeFee;
import parkingFloor.ParkingFloor;
import parkingLot.ParkingLot;
import parkingSpot.ParkingSpot;
import vehicle.SpotSize;
import vehicle.Vehicle;
import vehicle.VehiclePriority;
import vehicle.VehicleType;

public class SmartParkingLotSystemDemo {
    public static void main(String[] args)
    {
        System.out.println("Adding Vehicles to the Parking Lot System");
        Vehicle bike=new Vehicle.VehicleBuilder()
                .setVehicleNumber("KA-01-AB-1234")
                .setVehicleModel("Yamaha FZ")
                .setVehicleType(VehicleType.MOTORCYCLE)
                .setVehicleOwner("John Doe")
                .setPhoneNumber("9876543210")
                .setSuitableSpotSize(SpotSize.SMALL).setPriority(VehiclePriority.HIGH)
                .build();
        Vehicle car= new Vehicle.VehicleBuilder().setVehicleNumber("KA-02-CD-5678")
                .setVehicleModel("Honda City")
                .setVehicleType(VehicleType.CAR)
                .setVehicleOwner("Jane Smith")
                .setPhoneNumber("8765432109")
                .setSuitableSpotSize(SpotSize.MEDIUM).setPriority(VehiclePriority.LOW)
                .build();
        Vehicle bus= new Vehicle.VehicleBuilder().setVehicleNumber("KA-03-EF-9012")
                .setVehicleModel("Volvo B9R")
                .setVehicleType(VehicleType.BUS)
                .setVehicleOwner("Alice Johnson")
                .setPhoneNumber("7654321098")
                .setSuitableSpotSize(SpotSize.LARGE).setPriority(VehiclePriority.MEDIUM)
                .build();
        Vehicle truck= new Vehicle.VehicleBuilder().setVehicleNumber("KA-04-GH-3456")
                .setVehicleModel("Tata Ace")
                .setVehicleType(VehicleType.TRUCK)
                .setVehicleOwner("Bob Brown")
                .setPhoneNumber("6543210987")
                .setSuitableSpotSize(SpotSize.LARGE).setPriority(VehiclePriority.HIGH)
                .build();
        System.out.println("Printing Vehicle Details");

        bike.getVehicleDetailsDisplayPanel().showDetails();
        car.getVehicleDetailsDisplayPanel().showDetails();
        bus.getVehicleDetailsDisplayPanel().showDetails();
        truck.getVehicleDetailsDisplayPanel().showDetails();

        System.out.println("Creating Parking Lot with 3 Floors, each having 3 spots");
        ParkingLot parkingLot=new ParkingLot();
        ParkingFloor parkingFloor1=new ParkingFloor("Floor-1",parkingLot);
        ParkingFloor parkingFloor2=new ParkingFloor("Floor-2",parkingLot);
        ParkingFloor parkingFloor3=new ParkingFloor("Floor-3",parkingLot);

        ParkingSpot parkingSpot1Floor1=new ParkingSpot.ParkingSpotBuilder(parkingFloor1).setSpotSize(SpotSize.SMALL).setPriority(VehiclePriority.HIGH).build();
        ParkingSpot parkingSpot2Floor1=new ParkingSpot.ParkingSpotBuilder(parkingFloor1).setSpotSize(SpotSize.MEDIUM).setPriority(VehiclePriority.MEDIUM).build();
        ParkingSpot parkingSpot3Floor1 =new ParkingSpot.ParkingSpotBuilder(parkingFloor1).setSpotSize(SpotSize.LARGE).setPriority(VehiclePriority.LOW).build();
        parkingSpot1Floor1.getParkingSpotDisplayPanel().showDetails();
        parkingSpot2Floor1.getParkingSpotDisplayPanel().showDetails();
        parkingSpot3Floor1.getParkingSpotDisplayPanel().showDetails();

        parkingFloor1.addOrUpdateParkingSpot(parkingSpot1Floor1);
        parkingFloor1.addOrUpdateParkingSpot(parkingSpot2Floor1);
        parkingFloor1.addOrUpdateParkingSpot(parkingSpot3Floor1);
        parkingFloor1.getParkingFloorDisplayPanel().showDetails();

        ParkingSpot parkingSpot1Floor2=new ParkingSpot.ParkingSpotBuilder(parkingFloor2).setSpotSize(SpotSize.SMALL).setPriority(VehiclePriority.LOW).build();
        ParkingSpot parkingSpot2Floor2=new ParkingSpot.ParkingSpotBuilder(parkingFloor2).setSpotSize(SpotSize.MEDIUM).setPriority(VehiclePriority.HIGH).build();
        ParkingSpot parkingSpot3Floor2=new ParkingSpot.ParkingSpotBuilder(parkingFloor2).setSpotSize(SpotSize.LARGE).build();
        parkingSpot1Floor2.getParkingSpotDisplayPanel().showDetails();
        parkingSpot2Floor2.getParkingSpotDisplayPanel().showDetails();
        parkingSpot3Floor2.getParkingSpotDisplayPanel().showDetails();

        parkingFloor2.addOrUpdateParkingSpot(parkingSpot1Floor2);
        parkingFloor2.addOrUpdateParkingSpot(parkingSpot2Floor2);
        parkingFloor2.addOrUpdateParkingSpot(parkingSpot3Floor2);
        parkingFloor2.getParkingFloorDisplayPanel().showDetails();

        ParkingSpot parkingSpot1Floor3=new ParkingSpot.ParkingSpotBuilder(parkingFloor3).setSpotSize(SpotSize.SMALL).build();
        ParkingSpot parkingSpot2Floor3=new ParkingSpot.ParkingSpotBuilder(parkingFloor3).setSpotSize(SpotSize.MEDIUM).build();
        ParkingSpot parkingSpot3Floor3=new ParkingSpot.ParkingSpotBuilder(parkingFloor3).setSpotSize(SpotSize.LARGE).build();
        parkingSpot1Floor3.getParkingSpotDisplayPanel().showDetails();
        parkingSpot2Floor3.getParkingSpotDisplayPanel().showDetails();
        parkingSpot3Floor3.getParkingSpotDisplayPanel().showDetails();

        parkingFloor3.addOrUpdateParkingSpot(parkingSpot1Floor3);
        parkingFloor3.addOrUpdateParkingSpot(parkingSpot2Floor3);
        parkingFloor3.addOrUpdateParkingSpot(parkingSpot3Floor3);
        parkingFloor3.getParkingFloorDisplayPanel().showDetails();

        parkingLot.addOrUpdateParkingFloor(parkingFloor1);
        parkingLot.addOrUpdateParkingFloor(parkingFloor2);
        parkingLot.addOrUpdateParkingFloor(parkingFloor3);
        parkingLot.getParkingLotDisplayPanel().showDetails();

        System.out.println("Parking Vehicles");
        Ticket ticket = parkingLot.getEntryExitPanel().parkVehicle(bike,new PriorityBasedStrategy(),parkingLot);
        ticket.getTicketDetailsDisplayPanel().showDetails();
        Ticket ticket1 = parkingLot.getEntryExitPanel().parkVehicle(car,new PriorityBasedStrategy(),parkingLot);
        ticket1.getTicketDetailsDisplayPanel().showDetails();

        System.out.println("Unparking Vehicles");
        parkingLot.getEntryExitPanel().unParkVehicle(bike,ticket,new PriorityBasedStrategy(),new VehicleTypeFee());
        parkingLot.getEntryExitPanel().unParkVehicle(bike,ticket1,new PriorityBasedStrategy(),new VehicleTypeFee());
    }
}
