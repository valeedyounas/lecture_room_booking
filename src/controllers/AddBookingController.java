package controllers;

import application.mainFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import application.Main;
import database.MySQLDatabase;
import javafx.event.ActionEvent;
import misc.Booking;
import misc.Lecturer;
import misc.Room;
import misc.Staff;

public class AddBookingController {

	Main main = mainFactory.createMain();

	@FXML
	Label lbl;
	@FXML
	TextField l_id;
	@FXML
	TextField r_id;
	@FXML
	TextField s_id;
	@FXML
	TextField duration;
	@FXML
	TextField expected_attendees;
	@FXML
	TextArea reason;
	@FXML
	DatePicker date;
	@FXML
	TextField time;
	@FXML
	public void handleSave(ActionEvent event)
	{
		if(validateInput())
		{


			try {

				// SERVER KO BHEJO BOOKING DETAILS
				Booking b = new Booking();
				Staff s = new Staff();
				s.setId(Integer.parseInt(s_id.getText()));
				b.setStaff(s);
				Lecturer l = new Lecturer();
				l.setId(Integer.parseInt(l_id.getText()));
				b.setLecturer(l);
				Room r = new Room();
				r.setId(Integer.parseInt(r_id.getText()));
				b.setRoom(r);
				b.setReason_booking(reason.getText());
				b.setExpected_attendees(Integer.parseInt(expected_attendees.getText()));
				b.setDuration(Integer.parseInt(duration.getText()));
				b.setTime(time.getText());
				b.setDate(date.getValue().toString());


				try {
					main.client.send_toServer(b);
					main.client.send_toServer("ADD");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Object o = main.client.receive_fromServer();
				boolean rs = false;
				if (o != null) {
					rs = (boolean) o;
				}
				if (rs) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Room Booking");
					alert.setHeaderText("Room booked");
					alert.setContentText("This Room has been booked successfully");
					alert.showAndWait();
					handleCancel(event);
				}else{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Room Booking");
					alert.setHeaderText("Room not booked");
					alert.setContentText("This Room has not been booked successfully");
					alert.showAndWait();
					handleCancel(event);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

        if (l_id.getText().equals("") || r_id.getText().equals("")|| s_id.getText().equals("") || duration.getText().equals("") || reason.getText().equals(""))

            {
        		errorMessage += "Please enter Credentials!\n";
            }



        if (errorMessage.length() == 0)
        {
            return true;
        } else
        {
            lbl.setText(errorMessage);
            return false;
        }
    }
}
