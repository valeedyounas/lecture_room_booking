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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeleteBookingController {
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
	public void findBooking() {

		// SERVER KO BHEJO BOOKING DETAILS
		Booking b = new Booking();
		b.setId(Integer.parseInt(b_id.getText()));
		try {
			main.client.startServer_communication();
			main.client.send_toServer(b);
			main.client.send_toServer("DELETE ID");
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





		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Room Booking Deletion");
			alert.setHeaderText("Booking not deleted");
			alert.setContentText("Booking with this ID not found. Try Again!");
			alert.showAndWait();

		}
	}
	@FXML
	public void handleSave(ActionEvent event) {
		Booking bnew = new Booking();
		try {
				bnew.setId(Integer.parseInt(b_id.getText()));
			try {
				main.client.send_toServer(bnew);
				main.client.send_toServer("DELETE");
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
				alert.setTitle("Room Booking Deletion");
				alert.setHeaderText("Room booking Deleted!");
				alert.setContentText("This Room booking has been deleted successfully");
				alert.showAndWait();
				handleCancel(event);

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Room Booking Deletion");
				alert.setHeaderText("Booking not deleted");
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
