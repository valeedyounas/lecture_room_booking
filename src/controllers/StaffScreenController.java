package controllers;

import application.Main;
import application.mainFactory;
import client.ServerCommunicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import server.Services;


import java.io.IOException;
import java.sql.ResultSet;

public class StaffScreenController {

    Main main = mainFactory.createMain();


    @FXML
    public void listBookings() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../gui/BookingReport.fxml"));
            Scene scene = new Scene(root);
            Main.Get_Stage().setScene(scene);
            Main.Get_Stage().show();
            Main.Get_Stage().setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateBooking() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../gui/BookingReport.fxml"));
            Scene scene = new Scene(root);
            Main.Get_Stage().setScene(scene);
            Main.Get_Stage().show();
            Main.Get_Stage().setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBooking() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../gui/BookingReport.fxml"));
            Scene scene = new Scene(root);
            Main.Get_Stage().setScene(scene);
            Main.Get_Stage().show();
            Main.Get_Stage().setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void addBooking(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/StaffScreen.fxml"));
        Scene scene = new Scene(root);
        Main.Get_Stage().setScene(scene);
        Main.Get_Stage().show();
        Main.Get_Stage().setMaximized(true);

    }


    @FXML
    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));
        Scene scene = new Scene(root);
        Main.Get_Stage().setScene(scene);
        Main.Get_Stage().setTitle("Staff Screen");
        Main.Get_Stage().show();
        main.client.close_connection();

    }
}
