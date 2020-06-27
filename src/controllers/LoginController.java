/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import application.mainFactory;
import javafx.event.ActionEvent;
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

public class LoginController implements Initializable {
      public static boolean validateName(String name)
    {
        name = name.toLowerCase();
        int l1 = name.length();
        name = name.replaceAll("[*0-9?!.@#$%,^&*(){]", "");
        int l2 = name.length();
        if (l1 == l2)
            return true;
        else
            return false;
    }
    public static boolean validatePhoneNumber(String number)
    {
        number = number.toLowerCase();
        int l1 = number.length();
        number = number.replaceAll("[*A-Za-z?!.@#$%,^&*]", "");
        int l2 = number.length();
        if (l1 == l2 && l1 > 8 && l1 < 13)
            return true;
        else
            return false;
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
    public static String emailAddress;

    Main main = mainFactory.createMain();
  //  public static Cart CarT;


    @FXML
    private void handleButtonLogIn(ActionEvent event) throws SQLException, IOException
    {
        if(!emailLogIn.getText().trim().equals(""))
        {
            l1LogIn.setText("");
            if(!passwordLogIn.getText().trim().equals(""))
            {
                l2LogIn.setText("");
                int cashierID = Integer.parseInt(emailLogIn.getText());
                boolean verified = main.cs.verify(cashierID, passwordLogIn.getText());
                if (verified)
                {

                    Stage stage;
                    Parent root;
                    stage = (Stage) LogIn.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("CartScreen.fxml"));
                    Scene sc = new Scene(root);
                    sc.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
                    stage.setScene(sc);
                    stage.show();
                }
                else
                {
                    l1LogIn.setText("LogIn Failed");
                }
            }
            else
                l2LogIn.setText("Invalid");
        }
        else
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
    TextField emailSignUp = new TextField();
    @FXML
    TextField PhoneSignUp = new TextField();
    @FXML
    TextField nameSignUp = new TextField();
    @FXML
    PasswordField passwordSignUp = new PasswordField();

  @FXML
    private void handleButtonSignUp(ActionEvent event) throws SQLException
    {
	String  name = nameSignUp.getText();
	String emailAddress = emailSignUp.getText();
	String  phoneNumber = PhoneSignUp.getText();
	String designation = "Cashier";
	String address = "";
	String password = passwordSignUp.getText();
        l6SignUp.setText("");
        if(!nameSignUp.getText().trim().equals("") && validateName(nameSignUp.getText().toString()))
        {
            l3SignUp.setText("");
            if(!emailSignUp.getText().trim().equals(""))
            {
                l4SignUp.setText("");
                if(!PhoneSignUp.getText().trim().equals("") && validatePhoneumber(PhoneSignUp.getText().toString()))
                {
                    l5SignUp.setText("");
                    if(!passwordSignUp.getText().trim().equals(""))
                    {


                        main.getCs().insert(name, emailAddress, phoneNumber, designation, address, password);
                            l6SignUp.setText("Account Created Successfully");
                            nameSignUp.setText("");
                            emailSignUp.setText("");
                            PhoneSignUp.setText("");
                            passwordSignUp.setText("");

                      }
//                        System.out.println(inserted);

                    else
                        l6SignUp.setText("Field Empty");
                }
                else
                    l5SignUp.setText("Invalid");
            }
            else
                l4SignUp.setText("Invalid");
        }
        else
            l3SignUp.setText("Invalid");

    }


    @FXML
    Button adminPanel = new Button();
    @FXML
    private void handleButtonAdminPanel(ActionEvent event) throws IOException
    {
        Stage stage;
        Parent root;
        stage = (Stage) adminPanel.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AdminPanelLogin.fxml"));
        Scene sc = new Scene(root);
        sc.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        stage.setScene(sc);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
