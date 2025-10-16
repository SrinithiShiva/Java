package parkingLot;

import java.util.ArrayList;

import displayPanel.ParkingLotDisplayPanel;
import parkingFloor.ParkingFloor;

public class ParkingLot {
    private ArrayList<ParkingFloor> parkingFloorList;
    private EntryExitPanel entryExitPanel;
    private ParkingLotDisplayPanel parkingLotDisplayPanel;

    public ParkingLot(final ArrayList<ParkingFloor> parkingFloorList, final EntryExitPanel entryExitPanel) {
        this.parkingFloorList = parkingFloorList ;
        this.entryExitPanel = entryExitPanel;
        this.parkingLotDisplayPanel = new ParkingLotDisplayPanel(this);
    }
    public ParkingLot(){
        this(new ArrayList<>(),new EntryExitPanel());
    }
    public ArrayList<ParkingFloor> getParkingFloorList() {
        return this.parkingFloorList;
    }
    public EntryExitPanel getEntryExitPanel() {
        return this.entryExitPanel;
    }
    public void addOrUpdateParkingFloor(ParkingFloor parkingFloor){
        this.parkingFloorList.add(parkingFloor);
    }
    public ParkingLotDisplayPanel getParkingLotDisplayPanel() {
        return this.parkingLotDisplayPanel;
    }
}
