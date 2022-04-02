package Modules.Booking;

import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;
import Modules.Booking.model.Booking;
import Utils.GoogleMap;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class BookingView {
    ParkingSlotUtils parkingSlotUtils = new ParkingSlotUtils(Constants.parkingSlotQueryBuilderDAO);

    public void displayBookings(ArrayList<Booking> bookings) {
        ArrayList<Booking> pastBookings = new ArrayList<>();
        ArrayList<Booking> upcomingBookings = new ArrayList<>();
        Date currentDate = new Date();
        bookings.forEach((b) -> {
            if (b.getBooking_date().after(currentDate) && b.getStart_time().after(currentDate)) {
                upcomingBookings.add(b);
            } else {
                pastBookings.add(b);
            }
        });

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("                        *** Upcoming Bookings ***                        ");
        System.out.println("-------------------------------------------------------------------------");
        printBookings(upcomingBookings);
        System.out.println("\n\n");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("                           *** Past Bookings ***                         ");
        System.out.println("-------------------------------------------------------------------------");
        printBookings(pastBookings);
    }

    public void printBookings(ArrayList<Booking> bookings) {
        if (bookings.size() == 0) {
            System.out.println("-------------------------------------------------------------------------");
            Constants.printAndSpeak("There are no bookings");
            System.out.println("-------------------------------------------------------------------------");
        } else {
            bookings.forEach((b) -> {
                ParkingSlot parkingSlot = null;
                try {
                    parkingSlot = parkingSlotUtils.getParkingSlotById(b.getParking_id());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Booking ID: " + b.getReference_id());
                System.out.println("Booking Date: " + b.getBooking_date());
                System.out.println("Start Time: " + b.getStart_time());
                System.out.println("End Time: " + b.getEnd_time());
                System.out.println("Parking Slot Location: " + GoogleMap.generateUrl(parkingSlot.address));
                System.out.println("-------------------------------------------------------------------------");
            });
        }
    }
}
