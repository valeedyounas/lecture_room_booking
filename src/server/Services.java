package server;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import misc.Booking;
import misc.Lecturer;
import misc.Room;
import database.MySQLDatabase;
import misc.Staff;

import java.io.Serializable;
import java.sql.ResultSet;
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
           // db.updateRoom(booking.getRoom().getId(), 1);
           // System.out.println("v");
            return true;
        }
        return false;
    }

    private static ArrayList<Booking> prepare_bookings(ArrayList<ArrayList<String>> l) {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        for (int i = 0; i < l.size(); i++) {
            Booking b = new Booking();
            b.setId(Integer.parseInt(l.get(i).get(0)));
            b.setDate(l.get(i).get(1));
            b.setTime(l.get(i).get(2));
            b.setDuration(Integer.parseInt(l.get(i).get(3)));
            b.setReason_booking(l.get(i).get(4));
            b.setExpected_attendees(Integer.parseInt(l.get(i).get(5)));

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

    public static ArrayList<Booking> list_allBookings() {
        return prepare_bookings(db.getRows("Booking"));
    }


    public static class Requirements implements Serializable {
        public int capacity;
        public String type;
        public String date;
    }


    private static ArrayList<Room> prepare_rooms(ArrayList<ArrayList<String>> l) {
        ArrayList<Room> rooms = new ArrayList<Room>();
        for (int i = 0; i < l.size(); i++) {
            Room room = new Room();
            room.setId(Integer.parseInt(l.get(0).get(0)));
            room.setRoom_no(l.get(0).get(1));
            room.setCapacity(Integer.parseInt(l.get(0).get(2)));
            room.setStatus(Integer.parseInt(l.get(0).get(3)));
            room.setRoom_type(l.get(0).get(4));
            rooms.add(room);
        }
        return rooms;

    }

    public static ArrayList<Booking> list_roomBookings(Room room) {
        return  prepare_bookings(db.getIndexValue("Booking", "room_id", room.getId()));

    }

    public static ArrayList<Booking> list_dayBookings(Requirements r) {
        return prepare_bookings(db.getIndexValue("Booking","date",r.date));
    }

    public static ArrayList<Booking> list_dayBookings(String date, int r_id) {
        return prepare_bookings(db.getIndexValue("Booking","date",date,"room_id",r_id));
    }
    public static  ArrayList<Room> list_availableRooms(Requirements r) {
        String query = "select * from `room` where ( `type` ='" + r.type + "' and `capacity` >= " + r.capacity + ")";
        return  prepare_rooms(db.executeSelect(query));
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
    public static Booking get_booking(Booking booking) {
        //True: added successfully
        //False: failed, double booking
        System.out.println(booking.getId());
        ArrayList<Booking> b = prepare_bookings(db.getIndexValue("Booking","id",booking.getId()));
        if (b.size()!=0)
            return b.get(0);
        return null;
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
