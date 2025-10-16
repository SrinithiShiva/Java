package feeManagement.calculator;

import java.time.Duration;

import feeManagement.Ticket;

public class TimeBasedFee implements FeeCalculator {

    @Override
    public void calculateFee(Ticket ticket) {
        double fees =  Duration.between(ticket.getExitTime(),ticket.getEntryTime()).toHours() * 120.0;
        ticket.setFee(fees);
    }
}
