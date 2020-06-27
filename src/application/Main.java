package application;

import database.MySQLDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.desktop.AppForegroundListener;

public class Main extends Application {
    public static Stage s;

    @Override
    public void start(Stage primaryStage) {
        try {
          /*  Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));//LoginMainScreen
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();*/
            MySQLDatabase.getInstance("jdbc:mysql://localhost/LectureRoomBooking", "root", "tiger");
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 300, 275));
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
