package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLDatabase {
	private Connection dbConn;
	private Statement stmt;

	private static MySQLDatabase dbConnection;

	public static MySQLDatabase getInstance(String url,String user,String password)
	{
		if (dbConnection == null)
		{
			dbConnection = new MySQLDatabase(url , user, password);
		}
		return dbConnection;
	}

	public static MySQLDatabase getInstance() throws Exception
	{
		if (dbConnection == null)
		{
			throw new Exception();
		}
		return dbConnection;
	}


	private MySQLDatabase(String url, String user, String password)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConn = DriverManager.getConnection(url, user, password);
			stmt = dbConn.createStatement();
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}


	public ArrayList<ArrayList<String>> getRows(String tableName) throws SQLException
	{
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		String sqlQuery = new String();
		sqlQuery = "SELECT * FROM " + tableName;
		ResultSet rsRows = stmt.executeQuery(sqlQuery);
		while (rsRows.next()){
			rows.add(rowToColumns(rsRows));
		}
		rsRows.close();
		return rows;
	}

	public ArrayList<ArrayList<String>> getIndexValue(String tableName, String colName, String value) throws SQLException
	{
		String sqlQuery = new String();
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		sqlQuery = "Select * FROM " + tableName + " Where " + colName + " = '" + value + "'";
		System.out.println(sqlQuery);
		ResultSet rsRows = stmt.executeQuery(sqlQuery);
		while (rsRows.next()){
			rows.add(rowToColumns(rsRows));
		}
		rsRows.close();
		return rows;
	}

	public int removeRow(String tableName, String colName, String value) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "Delete FROM " + tableName + " Where " + colName + " = '" + value + "'";
		return stmt.executeUpdate(sqlQuery);
	}

	public int addStaff(String name, String password, String username,String phone, String address) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "INSERT INTO `staff`(`Name`, `Password`, `Phone`, `Address`) "
				+ "VALUES ('" + name + "', '" + password + "', '" + phone + "', '" + address + "' )";
		//System.out.println(sqlQuery);
		return stmt.executeUpdate(sqlQuery);
	}

	/*public int updateEmployee(String ID, String name, String password, String phone, String address,String type) throws SQLException
	{
		String sqlQuery = new String();
		/*sqlQuery = "INSERT INTO `employee`(`Name`, `Password`, `Phone`, `Address`) "
				+ "VALUES (" + name + "', '" + password + "', '" + phone + "', '" + address + "' )";
		sqlQuery = "UPDATE `employee` SET `Name` = '" + name + "',"
				+ "`Type` = '" + type +"',"
				+ " `Password` = '" + password + "', `Phone` = '" + phone +"', `Address` = '" + address +"' WHERE `employee`.`ID` = "+ ID +";";

		return stmt.executeUpdate(sqlQuery);
	}*/


	public int addBooking(String date, String time, int duration, String b_reason, int ex_atendees, int l_id, int r_id,int s_id) throws SQLException
	{

		String sqlQuery = new String();
		sqlQuery = "INSERT INTO `booking` (`date`, `time`, `duration`, `booking_reason`, `expected_atendees`,`lect_id`,`room_id`,`staff_id`)"
				+ " VALUES ('"  + date + "', '" + time + "', " + duration + ", '" + b_reason + "', " + ex_atendees +", " +l_id
				+"," +r_id+","+ s_id +  ")";
		System.out.println(sqlQuery);
		return stmt.executeUpdate(sqlQuery);
	}

	public int updateBooking(int ID, String date, String time, int duration, String b_reason, int ex_atendees, int l_id, int r_id,int s_id) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "UPDATE `booking` SET `Name`= '" + name
				+"' ,`Description`= '" + desc + "',`Price`= '" + price + "',`Qty`= '" + qty + "',`Place`= '" +
				place + "' WHERE `Code`=" + Integer.parseInt(ID) +";";
		System.out.println(sqlQuery);
		return stmt.executeUpdate(sqlQuery);
	}

	public int executeUpdate(String query)
	{
		try{
			return stmt.executeUpdate(query);
		}
		catch (SQLException ex){
			return -1;
		}
	}




	public void addSale() throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "INSERT INTO `sale`(`total_amount`, `payment`) VALUES (0,0)";
		stmt.executeUpdate(sqlQuery);
	}
	public int addSale(String sale_code, String Ecode, String EName, String Edesc, int price, int qty, int subtotal) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "INSERT INTO `sale_line_item`(`sale_code`, `robot_code`, `robot_name`, `robot_desc`, `robot_price`, `qty`, `subtotal`) "
				+ "VALUES ('"+ sale_code +"', '" + Ecode +"', '"+ EName +"','"+ Edesc +"','"+ price +"','"+ qty +"','"+ subtotal +"');";
		System.out.println(sqlQuery);
		return stmt.executeUpdate(sqlQuery);
	}
	public int setSaleLineItemOnRobotCode(String sale_code, String ERobot_Code, String qty) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "UPDATE `sale` SET `ERobot_Qty`=" + qty + " WHERE `Sale_Code` = " + sale_code + " AND `ERobot_Code`= " + ERobot_Code+ ";";
		return stmt.executeUpdate(sqlQuery);
	}

	public int getCurrentSale() throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "SELECT MAX(sale_code) FROM sale;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		//Cuz it points to -1th index in the begining
		rs.next();
		return rs.getInt(1);

	}

	public int addCustomDemand(String demand_qty, String customer_name, String customer_phone_no, String item_name, String item_desc )
	{
		try
		{
			String sqlQuery = new String();
			sqlQuery = "INSERT INTO `erobot_demand`(`Demand_Qty`, `Customer_Name`, `Customer_Phone_no`, `Item_Name`, `Item_Desc`) VALUES ('" +
					 demand_qty +"', '"+ customer_name +"','"+ customer_phone_no +"','"+ item_name +"','"+ item_desc + "');";
//			System.out.println(sqlQuery);
			return stmt.executeUpdate(sqlQuery);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	public ArrayList<String> getSaleLineItemInfo(String sale_code, String robot_code) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "SELECT * FROM `sale_line_item` WHERE robot_code = " + robot_code + " AND sale_code = " + sale_code + ";";
		ResultSet rsRows = stmt.executeQuery(sqlQuery);
		rsRows.next();
		return rowToColumns(rsRows);
	}

	public void updateSaleLineItem(String sale_code, String robot_code, String qty, String sub_total) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "UPDATE `sale_line_item` SET `qty`= "+ qty + ",`subtotal`= " + sub_total
				+ " WHERE sale_code = " + sale_code + " AND robot_code = "+ robot_code + ";";
//		System.err.println(sqlQuery);
		stmt.executeUpdate(sqlQuery);
	}

	public void removeSaleLineItem(String sale_code, String robot_code) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "DELETE FROM `sale_line_item` WHERE sale_code = " + sale_code + " AND robot_code = " + robot_code + ";";
//		System.err.println(sqlQuery);
		stmt.executeUpdate(sqlQuery);

	}

	public int getTotalAmount(int sale_code) throws SQLException
	{

		String sqlQuery = new String();
		sqlQuery = "SELECT SUM(subtotal) FROM `sale_line_item` WHERE sale_code = " + sale_code + ";";
//		System.err.println(sqlQuery);
		ResultSet rs = stmt.executeQuery(sqlQuery);
		rs.next();
		return Integer.valueOf(rs.getInt(1));

	}

	public void runQuery(String query) throws SQLException
	{
		stmt.executeUpdate(query);
	}

	public void removeSale(int sale_code) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "DELETE FROM sale WHERE sale_code = " + sale_code + ";";
		stmt.executeUpdate(sqlQuery);
	}

	public ResultSet getSaleInfo(int sale_code) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "SELECT * FROM `sale_line_item` WHERE sale_code = " + sale_code + ";";
		return stmt.executeQuery(sqlQuery);
	}

	public void setSale(int sale_code, String total_amount, String payment ) throws SQLException
	{

		String sqlQuery = new String();
		sqlQuery = "UPDATE `sale` SET `total_amount`= " + total_amount + ",`payment`= " + payment + " WHERE sale_code = " + sale_code + ";";
		System.err.println(sqlQuery);
		stmt.executeUpdate(sqlQuery);
	}

	public ResultSet getDemands() throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "SELECT * from erobot_demand;";
//		System.err.println(sqlQuery);
		return stmt.executeQuery(sqlQuery);
	}

	//helper
	private ArrayList<String> rowToColumns(ResultSet row) throws SQLException
	{
		ArrayList<String> rowData = new ArrayList<String>();
		try
		{
			int columnCount = row.getMetaData().getColumnCount();
			for (int j = 1; j < columnCount+1; ++j){
				rowData.add(row.getString(j));
			}
		}
		catch (SQLException e)
		{
			System.out.println("No Data found");
		}
		return rowData;
	}
}
