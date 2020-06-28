package controllers;

import application.mainFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.sql.Statement;

import application.Main;
import database.MySQLDatabase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.text.Text;
import javafx.util.Callback;
import misc.Booking;
import misc.Room;
import server.Services;

public class BookingReportController implements Initializable {
    Main main = mainFactory.createMain();
    @FXML
    private TableView tableview;
    @FXML
    private Button cancel;
    @FXML
    private Label lba;
    @FXML
    private Label lbb;
    @FXML
    private Label lbc;

    private ObservableList<ObservableList> data;


    private void showViews(Services.Requirements r, String t) throws IOException, SQLException {
        data = null;
        data = FXCollections.observableArrayList();
        try {
            main.client.send_toServer(r);
            main.client.send_toServer(t);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object o = main.client.receive_fromServer();
        ArrayList<Booking> rs = null;
        if (o != null) {
            rs = (ArrayList<Booking>) o;
        }
        //System.out.println(rs.getMetaData().getColumnCount());
        ArrayList<TableColumn> columns = new ArrayList<>();


        columns.add(new TableColumn("Booking ID"));
        columns.add(new TableColumn("Room No."));
        columns.add(new TableColumn("Lecturer Name"));
        columns.add(new TableColumn("Staff Name"));

        columns.add(new TableColumn("Date"));
        columns.add(new TableColumn("Time"));
        columns.add(new TableColumn("Duration"));
        columns.add(new TableColumn("Booking Reason"));
		columns.add( new TableColumn("Expected Attendees") );

        for (int i = 0; i < columns.size(); i++) {
			final int j = i ;
            columns.get(i).setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableview.getColumns().addAll(columns.get(i));
        }

        for (int i = 0; i < rs.size(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList();

            row.add(Integer.toString(rs.get(i).getId()));
            row.add(rs.get(i).getRoom().getRoom_no());
			row.add( rs.get(i).getLecturer().getName());
			row.add( rs.get(i).getStaff().getName());

			row.add(rs.get(i).getDate());
			row.add(rs.get(i).getTime());
			row.add(Integer.toString(rs.get(i).getDuration()));
			row.add(rs.get(i).getReason_booking());
			row.add(Integer.toString(rs.get(i).getExpected_attendees()));

            data.add(row);
        }
        //FINALLY ADDED TO TableView
        tableview.setItems(data);

    }
    private void showViews( int k,Room r, String t) throws IOException, SQLException {
        data = null;
        data = FXCollections.observableArrayList();
        try {
            main.client.send_toServer(r);
            main.client.send_toServer(t);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object o = main.client.receive_fromServer();
        ArrayList<Booking> rs = null;
        if (o != null) {
            rs = (ArrayList<Booking>) o;
        }
        //System.out.println(rs.getMetaData().getColumnCount());
        ArrayList<TableColumn> columns = new ArrayList<>();


        columns.add(new TableColumn("Booking ID"));
        columns.add(new TableColumn("Room No."));
        columns.add(new TableColumn("Lecturer Name"));
        columns.add(new TableColumn("Staff Name"));

        columns.add(new TableColumn("Date"));
        columns.add(new TableColumn("Time"));
        columns.add(new TableColumn("Duration"));
        columns.add(new TableColumn("Booking Reason"));
        columns.add( new TableColumn("Expected Attendees") );

        for (int i = 0; i < columns.size(); i++) {
            final int j = i ;
            columns.get(i).setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableview.getColumns().addAll(columns.get(i));
        }

        for (int i = 0; i < rs.size(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList();

            row.add(Integer.toString(rs.get(i).getId()));
            row.add(rs.get(i).getRoom().getRoom_no());
            row.add( rs.get(i).getLecturer().getName());
            row.add( rs.get(i).getStaff().getName());

            row.add(rs.get(i).getDate());
            row.add(rs.get(i).getTime());
            row.add(Integer.toString(rs.get(i).getDuration()));
            row.add(rs.get(i).getReason_booking());
            row.add(Integer.toString(rs.get(i).getExpected_attendees()));

            data.add(row);
        }
        //FINALLY ADDED TO TableView
        tableview.setItems(data);

    }

    private void showViews(Room r, String t) throws IOException, SQLException {
        data = null;
        data = FXCollections.observableArrayList();
        try {
            main.client.send_toServer(r);
            main.client.send_toServer(t);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object o = main.client.receive_fromServer();
        ArrayList<Room> rs = null;
        if (o != null) {
            rs = (ArrayList<Room>) o;
        }
        //System.out.println(rs.getMetaData().getColumnCount());
        ArrayList<TableColumn> columns = new ArrayList<>();


        columns.add(new TableColumn("Room ID"));
        columns.add(new TableColumn("Room No."));
        columns.add(new TableColumn("Capacity"));
        columns.add(new TableColumn("Type"));

        for (int i = 0; i < columns.size(); i++) {
            final int j = i ;
            columns.get(i).setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableview.getColumns().addAll(columns.get(i));
        }

        for (int i = 0; i < rs.size(); i++) {
            ObservableList<String> row = FXCollections.observableArrayList();

            row.add(Integer.toString(rs.get(i).getId()));
            row.add(rs.get(i).getRoom_no());
            row.add( Integer.toString(rs.get(i).getCapacity()));
            row.add( rs.get(i).getRoom_type());
            data.add(row);
        }
        //FINALLY ADDED TO TableView
        tableview.setItems(data);

    }


    @FXML
    public void showAllBookings() {
        if (tableview != null) {
            tableview.getColumns().clear();
        }

        try {
            showViews(new Services.Requirements(), null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @FXML
    Button Room;
    @FXML
    TextField roomID;
    @FXML
    public void showBookingsByRoom() {
        if (tableview != null) {
            tableview.getColumns().clear();
        }

        try {
            Room r = new Room();
            r.setId(Integer.parseInt(roomID.getText()));
            showViews(0,r, null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }


    @FXML
    DatePicker date;
    @FXML
    Button byDate;

    @FXML
    public void showBookingsByDate() {
        if (tableview != null) {
            tableview.getColumns().clear();
        }

        try {
            Services.Requirements r = new Services.Requirements();
            r.date = date.getValue().toString();
            showViews(r, "LISTDAY");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }


    @FXML
    DatePicker date1;
    @FXML
    TextField roomType;
    @FXML
    TextField roomCapacity;
    @FXML
    Button searchByThese;

    @FXML
    public void showBookingsByThree() {
        if (tableview != null) {
            tableview.getColumns().clear();
        }

        try {
            Services.Requirements r = new Services.Requirements();
            r.date = date1.getValue().toString();
            r.capacity = Integer.parseInt(roomCapacity.getText().strip());
            r.type = roomType.getText();
            showViews(r, "AVAILABLE");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    // Event Listener on Button[#cancel].onAction
    @FXML
    public void cancelsale(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ManagerScreen.fxml"));
        Scene scene = new Scene(root, 1920, 990);

        Main.Get_Stage().setScene(scene);
        Main.Get_Stage().setTitle("Sales Report");
        Main.Get_Stage().show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
