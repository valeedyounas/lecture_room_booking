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
		// Send to server "ALL BOOKINGS"
		main.sc.sendConnectRequest();
		main.sc.sendTo_server( r );
		main.sc.sendTo_server(t);
		// Response from server
		ResultSet rs = (ResultSet) main.srt.getResponse();

		/**********************************
		 * TABLE COLUMN ADDED DYNAMICALLY *
		 **********************************/
		for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
			//We are using non property style for making dynamic table
			final int j = i;
			TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
			col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
				public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
					return new SimpleStringProperty(param.getValue().get(j).toString());
				}
			});

			tableview.getColumns().addAll(col);

		}

		/********************************
		 * Data added to ObservableList *
		 ********************************/
		while(rs.next()){
			//Iterate Row
			ObservableList<String> row = FXCollections.observableArrayList();
			for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++)
			{
				//Iterate Column
				row.add(rs.getString(i));
			}

			data.add(row);

		}

		//FINALLY ADDED TO TableView
		tableview.setItems(data);

	}

	private void showViews(Room r, String t) throws IOException, SQLException {
		data = null;
		data = FXCollections.observableArrayList();
		// Send to server "ALL BOOKINGS"
		main.sc.sendConnectRequest();
		main.sc.sendTo_server(r);
		main.sc.sendTo_server(t);
		// Response from server
		ResultSet rs = (ResultSet) main.srt.getResponse();

		/**********************************
		 * TABLE COLUMN ADDED DYNAMICALLY *
		 **********************************/
		for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
			//We are using non property style for making dynamic table
			final int j = i;
			TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
			col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
				public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
					return new SimpleStringProperty(param.getValue().get(j).toString());
				}
			});

			tableview.getColumns().addAll(col);

		}

		/********************************
		 * Data added to ObservableList *
		 ********************************/
		while(rs.next()){
			//Iterate Row
			ObservableList<String> row = FXCollections.observableArrayList();
			for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++)
			{
				//Iterate Column
				row.add(rs.getString(i));
			}

			data.add(row);

		}

		//FINALLY ADDED TO TableView
		tableview.setItems(data);

	}
	@FXML
	public void showAllBookings()
	{
		if(tableview!=null)
		{
		tableview.getColumns().clear();
		}

         try{
			showViews(new Services.Requirements(),null);
         }catch(Exception e){
             e.printStackTrace();
             System.out.println("Error on Building Data");
         }
	}

	@FXML Button Room;
	@FXML
	TextField roomID;

	@FXML
	public void showBookingsByRoom()
	{
		if(tableview!=null)
		{
			tableview.getColumns().clear();
		}

		try{
			Room r = new Room();
			r.setId(Integer.parseInt(roomID.getText()));
			showViews(r,null);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}


	@FXML DatePicker date;
	@FXML Button byDate;

	@FXML
	public void showBookingsByDate()
	{
		if(tableview!=null)
		{
			tableview.getColumns().clear();
		}

		try{
			Services.Requirements r = new Services.Requirements();
			r.date = date.getValue().toString();
			showViews(r,"LISTDAY");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
	}


	@FXML DatePicker date1;
	@FXML TextField roomType;
	@FXML TextField roomCapacity;
	@FXML Button searchByThese;
	@FXML
	public void showBookingsByThree()
	{
		if(tableview!=null)
		{
			tableview.getColumns().clear();
		}

		try{
			Services.Requirements r = new Services.Requirements();
			r.date = date1.getValue().toString();
			r.capacity  = Integer.parseInt(roomCapacity.getText().strip());
			r.type = roomType.getText();
			showViews(r,"AVAILABLE");
		}catch(Exception e){
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
