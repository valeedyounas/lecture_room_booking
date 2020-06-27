package controllers;

import application.Main;
import application.mainFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class StaffScreenController {

    Main main = mainFactory.createMain();

    @FXML
    public void addBooking(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/StaffScreen.fxml"));
        Scene scene = new Scene(root);
        Main.Get_Stage().setScene(scene);
        Main.Get_Stage().show();
        Main.Get_Stage().setMaximized(true);

    }



}
