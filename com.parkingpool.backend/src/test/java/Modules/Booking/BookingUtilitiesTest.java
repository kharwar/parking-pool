package Modules.Booking;

import Modules.Booking.controller.BookingController;
import Modules.Booking.model.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;


class BookingUtilitiesTest {

    @InjectMocks
    BookingController bu = mock(BookingController.class);

    @Test
    void bookingPositiveTest() {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            Booking booking = mock(Booking.class);
            BookingController bu = mock(BookingController.class);
            bu.book_slot(booking);
        });
    }

    @Test
    public void testEditBookingPositiveCase() {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            boolean returnValue = bu.edit_booking_date("1234", LocalDate.of(2023,2,23));
        });
    }

    @Test
    public void testDeleteBooking() throws SQLException {
        Assertions.assertDoesNotThrow(()->{
            Connection connection = mock(Connection.class);
            bu.delete_booking("123");
        });
    }
}
