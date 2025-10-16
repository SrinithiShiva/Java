package feeManagement;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.Supplier;

import displayPanel.Display;
import displayPanel.TicketDetailsDisplayPanel;
import parkingSpot.ParkingSpot;
import vehicle.Vehicle;

public class Ticket {
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private double fee;
    private TicketDetailsDisplayPanel ticketDetailsDisplayPanel;

    public Ticket(LocalDateTime entryTime, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.entryTime = entryTime;
        this.ticketId = generateTicketId.get();
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.fee = 0.0;
        this.ticketDetailsDisplayPanel = new TicketDetailsDisplayPanel(this);
    }
    private Supplier<String> generateTicketId = ()->{
        Random random = new Random();
        return "TICKET_".concat(String.valueOf(1000 + random.nextInt(9000)));
    };
    public LocalDateTime getEntryTime() {
        return this.entryTime;
    }
    public void setEntryTime(final LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
    public void setTicketId(final String ticketId) {
        this.ticketId = ticketId;
    }
    public String getTicketId() {
        return this.ticketId;
    }
    public Vehicle getVehicle() {
        return this.vehicle;
    }
    public void setVehicle(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public ParkingSpot getParkingSpot() {
        return this.parkingSpot;
    }
    public void setSpotId(final ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
    public double getFee() {
        return this.fee;
    }
    public void setFee(final double fee) {
        this.fee = fee;
    }
    public void setExitTime(final LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
    public LocalDateTime getExitTime() {
        return this.exitTime;
    }

    public Display<Ticket> getTicketDetailsDisplayPanel() {
        return this.ticketDetailsDisplayPanel;
    }
}
