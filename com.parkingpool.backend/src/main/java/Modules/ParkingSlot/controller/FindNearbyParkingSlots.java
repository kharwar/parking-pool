package Modules.ParkingSlot.controller;

import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;

import java.util.ArrayList;

public class FindNearbyParkingSlots {
    ParkingSlotUtils parkingSlotUtils = new ParkingSlotUtils(Constants.parkingSlotQueryBuilderDAO);
    public ArrayList<ParkingSlot> FindNearbyParkingSlots(double longitude, double latitude){

        return null;
    }
}
