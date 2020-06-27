package server;

import misc.Booking;
import misc.Room;

import java.util.ArrayList;

public class Services {
    public boolean add_booking(Booking booking) {
        //True: added successfully
        //False: failed, double booking
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
