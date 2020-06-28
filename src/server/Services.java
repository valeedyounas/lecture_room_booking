package server;

import javafx.beans.binding.StringBinding;
import misc.Booking;
import misc.Lecturer;
import misc.Room;
import database.MySQLDatabase;
import misc.Staff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Services {
    static MySQLDatabase db = MySQLDatabase.getInstance();

    public Services() throws Exception {
    }

    public static boolean add_booking(Booking booking) {
        //True: added successfully
        //False: failed, double booking
        int i = db.addBooking(booking.getDate(), booking.getTime(), booking.getDuration(), booking.getReason_booking(), booking.getExpected_attendees(),
                booking.getLecturer().getId(), booking.getRoom().getId(), booking.getStaff().getId());

        if (i > 0) {
            db.updateRoom(booking.getRoom().getId(), 1);
            System.out.println("v");
            return true;
        }
        return false;
    }

    public static ArrayList<Booking> prepare_bookings(ArrayList<ArrayList<String>> l) {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        for (int i = 0; i < l.size(); i++) {
            Booking b = new Booking();
            b.setId(Integer.parseInt(l.get(i).get(0)));
            b.setDate(l.get(i).get(1));
            b.setTime(l.get(i).get(2));
            b.setDuration(Integer.parseInt(l.get(i).get(3)));
            b.setExpected_attendees(Integer.parseInt(l.get(i).get(4)));
            b.setReason_booking(l.get(i).get(5));

            int l_id = Integer.parseInt(l.get(i).get(6));
            ArrayList<ArrayList<String>> row = db.getIndexValue("Lecturer", "id", l_id);
            Lecturer lecturer = new Lecturer();
            lecturer.setId(Integer.parseInt(row.get(0).get(0)));
            lecturer.setName(row.get(0).get(1));
            lecturer.setEmail(row.get(0).get(2));
            b.setLecturer(lecturer);

            int r_id = Integer.parseInt(l.get(i).get(7));
            row = db.getIndexValue("Room", "id", r_id);
            Room room = new Room();
            room.setId(Integer.parseInt(row.get(0).get(0)));
            room.setRoom_no(row.get(0).get(1));
            room.setCapacity(Integer.parseInt(row.get(0).get(2)));
            room.setStatus(Integer.parseInt(row.get(0).get(3)));
            room.setRoom_type(row.get(0).get(4));
            b.setRoom(room);

            int s_id = Integer.parseInt(l.get(i).get(8));
            row = db.getIndexValue("Staff", "id", s_id);
            Staff staff = new Staff();
            staff.setId(Integer.parseInt(row.get(0).get(0)));
            staff.setName(row.get(0).get(1));
            staff.setPassword(row.get(0).get(2));
            b.setStaff(staff);

            bookings.add(b);

        }
        return bookings;

    }


    public static class Requirements {
        public int capacity;
        public String type;
        public String date;
    }
    public static ResultSet list_allBookings() {
        return db.getResultSet("booking");
    }

    public static ResultSet list_roomBookings(Room room) {
        return  db.getResultSet("Booking", "room_id", room.getId());

    }


    public static ResultSet list_dayBookings(Requirements r) {
        return db.getResultSet("Booking", "date", r.date);
    }

    public static  ResultSet list_availableRooms(Requirements r) {

        String query = "select * from `room` where ( `type` ='" + r.type + "' and `capacity` >= " + r.capacity +
                " and `status`=0" + ")";
        return  db.executeSelect(query);
//        for (int i = 0; i < b.size(); i++) {
//
//            Room room = new Room();
//            room.setId(Integer.parseInt(b.get(i).get(0)));
//            room.setRoom_no(b.get(i).get(1));
//            room.setCapacity(Integer.parseInt(b.get(i).get(2)));
//            room.setStatus(Integer.parseInt(b.get(i).get(3)));
//            room.setRoom_type(b.get(i).get(4));
//            rooms.add(room);
//        }

    }


    public static boolean update_booking(Booking booking) {
        //True: added successfully
        //False: failed, double booking
        int i = db.updateBooking(booking.getId(), booking.getDate(), booking.getTime(), booking.getDuration(), booking.getReason_booking(), booking.getExpected_attendees(),
                booking.getLecturer().getId(), booking.getRoom().getId(), booking.getStaff().getId());
        if (i > 0)
            return true;
        return false;
    }

    public static boolean delete_booking(Booking booking) {
        //True: deleted successfully
        //False: failed, booking does not exist
        int i = db.removeRow("Booking", "id", booking.getId());
        if (i > 0)
            return true;
        return false;
    }


}
