package server;

import javax.sql.rowset.*;
import javax.sql.rowset.BaseRowSet;

import javafx.scene.control.TableColumn;
import misc.Booking;
import misc.Room;
import misc.Staff;

import java.io.Serializable;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReceiveThread extends Thread {
    private Socket connectSocket;
    private ClientCommunicator tempx;
    private Object client_details;
    private Object cmd;


    public Object getClientMsg() {
        return client_details;
    }

    private class ResultSetJ implements Serializable {
        public ResultSet rs;
    }

    public void run() {


        Object rs = null;

        if (connectSocket != null) {
            while (true) {
                client_details = null;

                try {
                    client_details = tempx.receiveFrom_otherClient(connectSocket);
                    cmd = tempx.receiveFrom_otherClient(connectSocket);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                while (client_details == null) ;
                System.out.println("object received");

                System.out.println(client_details.getClass().getName());
                if (client_details.getClass().getName().equals("misc.Staff")) {
                    //System.out.println();
                    Staff a = (Staff) client_details;
                    String command = (String) cmd;

                    if (command.compareTo("SIGNIN") == 0) {
                        //Write authentication code
                        boolean isVerified = Staff.verify(a.getId(), a.getPassword());
                        // System.out.println(a.getId()+a.getPassword());
                        //  System.out.println(isVerified);
                        try {
                            tempx.responseTo_OtherClient(isVerified, connectSocket);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (command.compareTo("SIGNUP") == 0) {

                        int isCreated = Staff.signUp(a);
                        try {
                            tempx.responseTo_OtherClient(isCreated, connectSocket);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }


                } else if (client_details.getClass().getName().equals("misc.Booking")) {
                    Booking b = (Booking) client_details;
                    String command = (String) cmd;

                    if (command.compareTo("ADD") == 0) {
                        //use Booking:b and add it to DB
                        boolean r = Services.add_booking(b);
                        try {
                            // if added successfully, send true to client
                            tempx.responseTo_OtherClient(r, connectSocket);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (command.compareTo("UPDATE ID") == 0) {
                        //use Booking:b to update
                        //if updated successfully send true to client
                        Booking bnew = Services.get_booking(b);
                        try {
                            tempx.responseTo_OtherClient(bnew, connectSocket);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (command.compareTo("UPDATE") == 0) {
                        //use Booking:b to update
                        //if updated successfully send true to client
                        boolean r = Services.update_booking(b);
                        try {
                            tempx.responseTo_OtherClient(r, connectSocket);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else if (command.compareTo("DELETE") == 0) {
                        //delete booking b
                        //if deleted successfully send true to client
                        boolean r = Services.delete_booking(b);
                        try {
                            tempx.responseTo_OtherClient(r, connectSocket);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                } else if (client_details.getClass().getName().equals("misc.Room")) {
                    Room p = (Room) client_details;
                    //return room bookings

                    try {
                        tempx.responseTo_OtherClient(Services.list_roomBookings(p), connectSocket);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (client_details instanceof Services.Requirements) {
                    Services.Requirements room_type = (Services.Requirements) client_details;
                    String command = (String) cmd;
                    if (command == null) {
                        rs = (ArrayList<Booking>) Services.list_allBookings();
                    } else if (command.compareTo("AVAILABLE") == 0) {
                        rs = (ArrayList<Room>) Services.list_availableRooms(room_type);

                    } else if (command.compareTo("LISTDAY") == 0) {
                        rs = (ArrayList<Booking>) Services.list_dayBookings(room_type);

                    }

                    try {

                        tempx.responseTo_OtherClient(rs, connectSocket);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public ReceiveThread(Socket x, ClientCommunicator a) {
        // TODO Auto-generated constructor stub
        connectSocket = x;
        tempx = a;
    }
}
