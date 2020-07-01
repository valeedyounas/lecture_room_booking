/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import misc.Staff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    //Main main = mainFactory.createMain();


    public static boolean validateName(String name) {
        name = name.toLowerCase();
        int l1 = name.length();
        name = name.replaceAll("[*0-9?!.@#$%,^&(){]", "");
        int l2 = name.length();
        return l1 == l2;
    }


    @FXML
    TextField emailLogIn = new TextField();
    @FXML
    PasswordField passwordLogIn = new PasswordField();
    @FXML
    Label l1LogIn = new Label();
    @FXML
    Label l2LogIn = new Label();
    @FXML
    Button LogIn = new Button();


    @FXML
    private void handleButtonLogIn() throws IOException {
        if (!emailLogIn.getText().trim().equals("")) {
            l1LogIn.setText("");
            if (!passwordLogIn.getText().trim().equals("")) {
                l2LogIn.setText("");
                int ID = Integer.parseInt(emailLogIn.getText());
                String pw = passwordLogIn.getText();
                Main.client.startServer_communication();
                //Response from server

                Staff s = new Staff();
                s.setId(ID);
                s.setPassword(pw);
                try {
                    Main.client.send_toServer(s);
                    Main.client.send_toServer("SIGNIN");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Object o = Main.client.receive_fromServer();
                boolean verified = false;
                if (o != null) {
                    verified = (boolean) o;
                }

                if (verified) {

                    Stage stage;
                    Parent root;
                    stage = (Stage) LogIn.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("../gui/StaffScreen.fxml"));
                    Scene sc = new Scene(root);
//                    sc.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
                    stage.setScene(sc);
                    stage.show();
                    Main.client.start_AcceptThread();
                } else {
                    l1LogIn.setText("Login Failed");
                }
            } else
                l2LogIn.setText("Invalid");
        } else
            l1LogIn.setText("Invalid");
    }


    @FXML
    Label l3SignUp = new Label();
    @FXML
    Label l4SignUp = new Label();
    @FXML
    Label l5SignUp = new Label();
    @FXML
    Label l6SignUp = new Label();
    @FXML
    Button SignUp = new Button();

    @FXML
    TextField nameSignUp = new TextField();
    @FXML
    PasswordField passwordSignUp = new PasswordField();

    @FXML
    private void handleButtonSignUp() {
        String name = nameSignUp.getText();
        String password = passwordSignUp.getText();
        l6SignUp.setText("");
        if (!nameSignUp.getText().trim().equals("") && validateName(nameSignUp.getText())) {
            l3SignUp.setText("");
            if (!passwordSignUp.getText().trim().equals("")) {
                //main.getCs().insert(name, password);
                Main.client.startServer_communication();
                Staff s = new Staff();
                s.setName(name);
                s.setPassword(password);
                try {
                    Main.client.send_toServer(s);
                    Main.client.send_toServer("SIGNUP");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Object o = Main.client.receive_fromServer();
                int response = -1;
                if (o != null) {
                    response = (int) o;
                }
                if (response != -1) {
                    l6SignUp.setText("Account Created Successfully\nPlease note your ID (for future login) which is: " + response);
                } else {
                    l6SignUp.setText("Account did not create Successfully");
                }
                nameSignUp.setText("");
                passwordSignUp.setText("");

            } else
                l6SignUp.setText("Field Empty");
        } else
            l5SignUp.setText("Invalid");
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
