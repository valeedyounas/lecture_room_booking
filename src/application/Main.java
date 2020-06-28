package application;

import client.ServerCommunicator;
import client.ServerReceive;
import database.MySQLDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage s;
    public static ServerCommunicator sc = new ServerCommunicator();
    public static ServerReceive srt ;

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/Login.fxml"));//LoginMainScreen
            Scene scene = new Scene(root);
            MySQLDatabase.getInstance("jdbc:mysql://localhost/lecture_room_booking", "root", "tiger");
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
       Main.srt =  new ServerReceive(Main.sc);
       srt.start();
        //initialize();
        launch(args);
    }

}
