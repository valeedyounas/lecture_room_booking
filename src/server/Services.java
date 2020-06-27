package server;

import misc.Booking;
import misc.Room;
import database.MySQLDatabase;

import java.sql.SQLException;
import java.util.ArrayList;



public class Services {
    MySQLDatabase db = MySQLDatabase.getInstance();

    public Services() throws Exception {
    }

    public boolean add_booking(Booking booking)  {
        //True: added successfully
        //False: failed, double booking
        db.addBooking(booking.getDate(),booking.getTime(),booking.getDuration(),booking.getReason_booking(),booking.getExpected_attendees(),
        booking.getLecturer().getId(),booking.getRoom().getId(),booking.getStaff().getId());
        return true;
    }

    public ArrayList<Booking> list_allBookings() {

        return null;
    }

    public ArrayList<Booking> list_roomBookings(Room room) {
        return null;
    }

    public ArrayList<Booking> list_dayBookings(String date) {
        return null;
    }

    public ArrayList<Booking> list_availableRooms(String type, String date) {
        return null;
    }

    public boolean update_booking(Booking booking) {
        //True: added successfully
        //False: failed, double booking
        return true;
    }
    public boolean delete_booking(Booking booking) {
        //True: deleted successfully
        //False: failed, booking does not exist
        return true;
    }


}
