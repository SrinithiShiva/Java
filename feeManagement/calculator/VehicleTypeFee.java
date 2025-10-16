package feeManagement.calculator;

import feeManagement.Ticket;
import vehicle.VehicleType;

public class VehicleTypeFee implements FeeCalculator {
    @Override
    public void calculateFee(Ticket ticket) {
        VehicleType vehicleType = ticket.getVehicle().getVehicleType();
        double fee = 0.0;
        switch (vehicleType) {
            case MOTORCYCLE:
                fee = 20.0;
                break;
            case CAR:
                fee = 50.0;
                break;
            case TRUCK:
                fee = 100.0;
                break;
            case TEMPO:
                fee = 80.0;
                break;
            case LORRY:
                fee = 120.0;
                break;
            case BUS:
                fee = 150.0;
                break;
            default:
                fee = 0.0;
                break;
        }
        ticket.setFee(fee);
    }
}
