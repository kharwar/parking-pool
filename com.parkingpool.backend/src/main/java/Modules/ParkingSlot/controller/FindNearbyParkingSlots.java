package Modules.ParkingSlot.controller;

import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;

import java.sql.SQLException;
import java.util.ArrayList;

public class FindNearbyParkingSlots {
    ParkingSlotUtils parkingSlotUtils = new ParkingSlotUtils(Constants.parkingSlotQueryBuilderDAO);

    public ArrayList<ParkingSlot> findNearbyParkingSlots(double longitude, double latitude) throws SQLException {

        ArrayList<ParkingSlot> nearbyParkingSlots=new ArrayList<ParkingSlot>();
        ArrayList<ParkingSlot> allParkingSlots= parkingSlotUtils.FindAllParkingSlots();
        double distanceBetweenTwoPoint=0;

        for (ParkingSlot parkingSlot:
             allParkingSlots) {
            distanceBetweenTwoPoint= parkingSlotUtils.calculateDistanceInMeters(latitude, longitude, parkingSlot.latitude, parkingSlot.longitude);
            if(distanceBetweenTwoPoint<10000)
            {
                nearbyParkingSlots.add(parkingSlot);
            }
        }
        return nearbyParkingSlots;
    }
}
