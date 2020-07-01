package controllers;

import application.Main;
import application.mainFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import misc.Booking;
import misc.Lecturer;
import misc.Room;
import misc.Staff;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;



public class UpdateBookingController {
    @FXML
    Label lbl;
    @FXML
    TextField b_id;
    @FXML
    TextField l_id;
    @FXML
    TextField r_id;
    @FXML
    TextField s_id;
    @FXML
    TextField roomType1;
    @FXML
    TextField duration;
    @FXML
    TextField expected_attendees;
    @FXML
    TextArea reason;
    @FXML
    DatePicker date;
    @FXML
    Button find;
    @FXML
    TextField time;
    Main main = mainFactory.createMain();
    @FXML
    Label dateLabel;

    @FXML
    public void findBooking() {

        // SERVER KO BHEJO BOOKING DETAILS
        Booking b = new Booking();
        b.setId(Integer.parseInt(b_id.getText()));
        try {
            main.client.startServer_communication();
            main.client.send_toServer(b);
            main.client.send_toServer("UPDATE ID");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object o = main.client.receive_fromServer();
        Booking bnew = null;
        if (o != null) {
            bnew = (Booking) o;
        }
        if (bnew != null) {
            b_id.setText(Integer.toString(bnew.getId()));
            l_id.setText(Integer.toString(bnew.getLecturer().getId()));
            r_id.setText(Integer.toString(bnew.getRoom().getId()));
            s_id.setText(Integer.toString(bnew.getStaff().getId()));
            duration.setText(Integer.toString(bnew.getDuration()));
            time.setText(bnew.getTime());
            reason.setText(bnew.getReason_booking());
            expected_attendees.setText(Integer.toString(bnew.getExpected_attendees()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(bnew.getDate(), formatter);
            //System.out.println(bnew.getDate());
            //System.out.println(localDate);
            //datePicker.setValue(localDate);
            date.setValue(localDate);
          //  dateLabel.setText(bnew.getDate());

       //     b_id.setEditable(true);
            b_id.setDisable(false);
      //      l_id.setEditable(true);
            l_id.setDisable(false);
      //      r_id.setEditable(true);
            r_id.setDisable(false);
            s_id.setDisable(false);
            duration.setDisable(false);
            time.setDisable(false);
            reason.setDisable(false);
            expected_attendees.setDisable(false);
            date.setDisable(false);

       //     datePicker.setDisable(false);
        //    datePicker.setEditable(true);




        }else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Room Booking Updation");
            alert.setHeaderText("Booking not updated");
            alert.setContentText("Booking with this ID not found. Try Again!");
            alert.showAndWait();

        }
    }
    @FXML
    public void handleSave(ActionEvent event) {
        Booking bnew = new Booking();
        try {
                Staff s = new Staff();
                s.setId(Integer.parseInt(s_id.getText()));
                bnew.setStaff(s);
                Lecturer l = new Lecturer();
                l.setId(Integer.parseInt(l_id.getText()));
                bnew.setLecturer(l);
                Room r = new Room();
                r.setId(Integer.parseInt(r_id.getText()));
                bnew.setRoom(r);
                bnew.setReason_booking(reason.getText());
                bnew.setExpected_attendees(Integer.parseInt(expected_attendees.getText()));
                bnew.setDuration(Integer.parseInt(duration.getText()));
                bnew.setTime(time.getText());
                bnew.setDate(date.getValue().toString());
                bnew.setId(Integer.parseInt(b_id.getText()));
                try {
                    main.client.send_toServer(bnew);
                    main.client.send_toServer("UPDATE");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Object o = main.client.receive_fromServer();
               boolean bc = false;
                if (o != null) {
                    bc = (boolean) o;
                }
                if (bc) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Room Booking Updation");
                    alert.setHeaderText("Room booking Updated!");
                    alert.setContentText("This Room booking has been updated successfully");
                    alert.showAndWait();
                    handleCancel(event);

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Room Booking Update");
                alert.setHeaderText("Booking not updated");
                alert.setContentText("Unexpected Error occurred, Try Again!");
                alert.showAndWait();
                //handleCancel(event);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    // Event Listener on Button[#cancel].onAction
    @FXML
    public void handleCancel(ActionEvent event) throws IOException {
        //System.out.println("Authenticate");
        Parent root = FXMLLoader.load(getClass().getResource("../gui/StaffScreen.fxml"));
        Scene scene = new Scene(root);
        Main.Get_Stage().setScene(scene);
        Main.Get_Stage().show();

    }

    public boolean validateInput() {

        String errorMessage = "";

        if (l_id.getText().equals("") || r_id.getText().equals("") || s_id.getText().equals("") || duration.getText().equals("") || reason.getText().equals("")) {
            errorMessage += "Please enter Credentials!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            lbl.setText(errorMessage);
            return false;
        }
    }
}
