package controllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class UpdateBookingController {
	@FXML Label lbl;
	@FXML TextField b_id;
	@FXML
	TextField l_id;
	@FXML
	TextField r_id;
	@FXML
	TextField s_id;
	@FXML TextField roomType1;
	@FXML
	TextField duration;
	@FXML
	TextField expected_attendees;
	@FXML
	TextArea reason;
	@FXML
	DatePicker datePicker1;
	@FXML
	TextField time;
	@FXML
	public void handleSave(ActionEvent event)
	{

		//SERVER WALA KAAM



		if(validateInput())
		{

			// Insert Query for insertin data in to datbase
			try {



				// SERVER KO UDATE BOOKING DETAILS

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Room Booking");
				alert.setHeaderText("Room booked");
				alert.setContentText("This Room has been booked successfully");
				alert.showAndWait();
				handleCancel(event);
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
