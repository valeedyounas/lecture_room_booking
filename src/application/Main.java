package application;

import client.Demo;
import client.ServerCommunicator;
import client.ServerReceive;
import database.MySQLDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ClientCommunicator;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Application {
    public static Stage s;
    public static InetAddress ip;

    static {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static int serverPort = 4000;
    public static int thisPort = 5000;
    public static Demo client = new Demo();

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));//LoginMainScreen
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static Stage Get_Stage() {
       return s;
   }


    public static void main(String[] args) {

        //initialize();
        launch(args);
    }

}
