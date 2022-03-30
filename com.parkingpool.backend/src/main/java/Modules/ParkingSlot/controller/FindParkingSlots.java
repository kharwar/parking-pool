package Modules.ParkingSlot.controller;

import Modules.Booking.model.Booking;
import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FindParkingSlots {
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

    public ArrayList<ParkingSlot> findAvailableParkingSlots(double longitude, double latitude, Date date, LocalTime startTime, LocalTime endTime) throws SQLException {
        String getBookedSlotsQuery = "SELECT * FROM booking WHERE date >= \"" + date + "\" and (end_time <= \""  + startTime + "\" or start_time <= \"" + endTime + "\")";
        ResultSet rs = Constants.stmt.executeQuery(getBookedSlotsQuery);
        ArrayList<Booking> bookedSlots = new ArrayList<Booking>();

        while(rs.next()){
            Booking booking = new Booking();
            booking.setParking_id(rs.getInt("parking_id"));
            booking.setBooking_date(rs.getDate("date"));
            booking.setOwner_id(rs.getInt("owner_user_id"));
            booking.setStart_time(rs.getTime("start_time"));
            booking.setUser_id(rs.getInt("client_user_id"));
            booking.setEnd_time(rs.getTime("end_time"));

            bookedSlots.add(booking);
        }

        ArrayList<ParkingSlot> nearbyParkingSlots = findNearbyParkingSlots(longitude, latitude);
        ArrayList<ParkingSlot> finalParkingSlots = new ArrayList<ParkingSlot>(nearbyParkingSlots);

        List<Integer> parking_ids = bookedSlots.stream().map(Booking::getParking_id).collect(Collectors.toList());

        for (ParkingSlot parkingSlot:
             nearbyParkingSlots) {
            if(isParkingSlotNotAcceptable(parking_ids, parkingSlot, startTime, endTime)){
                finalParkingSlots.remove(parkingSlot);
            }
        }
        return finalParkingSlots;
    }

    public ArrayList<ParkingSlot> filterAccordingToRate(ArrayList<ParkingSlot> parkingSlots){
        parkingSlots.sort(Comparator.comparing(ParkingSlot::getHourlyRate));
        return parkingSlots;
    }

    // ----- PRIVATE ITEMS -----
    private boolean isParkingSlotNotAcceptable(List<Integer> parking_ids, ParkingSlot parkingSlot ,LocalTime startTime, LocalTime endTime)  {
        return parking_ids.contains(parkingSlot.parking_slot_id) || ((parkingSlot.start_time.after(Time.valueOf(startTime)) && parkingSlot.end_time.before(Time.valueOf(endTime))));
    }
}
