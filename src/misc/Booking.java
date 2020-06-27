package misc;

public class Booking {
    private int id;
    private Room room;
    private Lecturer lecturer;
    private String date;
    private String time;
    private String duration;
    private String reason_booking;
    private int expected_attendees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReason_booking() {
        return reason_booking;
    }

    public void setReason_booking(String reason_booking) {
        this.reason_booking = reason_booking;
    }

    public int getExpected_attendees() {
        return expected_attendees;
    }

    public void setExpected_attendees(int expected_attendees) {
        this.expected_attendees = expected_attendees;
    }
}
