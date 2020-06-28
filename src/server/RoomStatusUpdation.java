package server;

import database.MySQLDatabase;
import misc.Booking;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomStatusUpdation extends Thread {
    MySQLDatabase db = MySQLDatabase.getInstance();
    public void run() {
        while (true) {
            String date = java.time.LocalDate.now().toString();
            Services.Requirements r = new Services.Requirements();
            r.date = date;
            ArrayList<Booking> lb = Services.list_dayBookings(r);
            String time_temp = java.time.LocalTime.now().toString();
            String[] time = time_temp.split(".");

            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date t1=null;
            try {
                t1= sdf.parse(time[0]);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println("Time: " + sdf.format(date));

            for (Booking b : lb) {
                String t = b.getTime();
                sdf = new SimpleDateFormat("hh:mm:ss");
                Date t2 = null;
                try {
                    t2 = sdf.parse(t);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                float diff = t1.getTime() - t2.getTime();
                if (diff>=b.getDuration()){
                    b.getRoom().setStatus(0);
                    db.updateRoom(b.getRoom().getId(),0);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
