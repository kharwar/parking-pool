package Modules.Booking;

import Utils.Constants;
import Modules.Booking.model.Booking;

import java.util.ArrayList;

public class BookingView {
    public void displayBookings(ArrayList<Booking> bookings){
        Constants.printAndSpeak("There are "+bookings.size()+" bookings");
        bookings.forEach((b) ->{
            String bookingDetail = "Booking reference id "+b.getReference_id()+" on date "+b.getBooking_date()+ " from "+b.getStart_time()+" to "+b.getEnd_time()+" for parking id "+b.getParking_id();
            Constants.printAndSpeak(bookingDetail);
        });
    }
}
