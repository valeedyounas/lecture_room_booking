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

    private static MySQLDatabase dbConnection = MySQLDatabase.getInstance("jdbc:mysql://localhost/lecture_room_booking", "root", "tiger");

    public static MySQLDatabase getInstance(String url, String user, String password) {
        if (dbConnection == null) {
            dbConnection = new MySQLDatabase(url, user, password);
        }
        return dbConnection;
    }

    public static MySQLDatabase getInstance()  {
        if (dbConnection == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dbConnection;
    }


    private MySQLDatabase(String url, String user, String password) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            dbConn = DriverManager.getConnection(url, user, password);
            stmt = dbConn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }
    }

    public ResultSet getResultSet(String tableName){
        String sqlQuery = "SELECT * FROM " + tableName;
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            return  rsRows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public ArrayList<ArrayList<String>> getRows(String tableName) {
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
        String sqlQuery = new String();
        sqlQuery = "SELECT * FROM " + tableName;
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            while (rsRows.next()) {
                rows.add(rowToColumns(rsRows));
            }
            rsRows.close();
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateRoom (int id , int status){
        String query = " update `room` set `status` = "+ status +" where `id`= " + id;
        int i = dbConnection.executeUpdate(query);
        if (i>0)
            return true;
        return false;
    }

    public ArrayList<ArrayList<String>> getAllBookings(String tableName) {
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
        String sqlQuery = new String();
        sqlQuery = "SELECT * FROM " + tableName;
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            while (rsRows.next()) {
                rows.add(rowToColumns(rsRows));
            }
            rsRows.close();
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getResultSet(String tableName, String colName, int value){
        String sqlQuery = "Select * FROM " + tableName + " Where " + colName + " = " + value + "";
        System.out.println(sqlQuery);
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            return rsRows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public ResultSet getResultSet(String tableName, String colName, String value){
        String sqlQuery = "Select * FROM " + tableName + " Where " + colName + " = '" + value + "'";
        System.out.println(sqlQuery);
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            return rsRows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public ArrayList<ArrayList<String>> getIndexValue(String tableName, String colName, int value) {
        String sqlQuery = new String();
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

        sqlQuery = "Select * FROM " + tableName + " Where " + colName + " = " + value + "";

        System.out.println(sqlQuery);
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            while (rsRows.next()) {
                rows.add(rowToColumns(rsRows));
            }
            rsRows.close();
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ArrayList<String>> getIndexValue(String tableName, String colName, String value) {
        String sqlQuery = new String();
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

        sqlQuery = "Select * FROM " + tableName + " Where " + colName + " = '" + value + "'";

        System.out.println(sqlQuery);
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            while (rsRows.next()) {
                rows.add(rowToColumns(rsRows));
            }
            rsRows.close();
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ArrayList<String>> getIndexValue(String tableName, String colName1, String value1, String colName2, String value2) {
        String sqlQuery = new String();
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

        sqlQuery = "Select * FROM " + tableName + " Where " + colName1 + " = '" + value1 + "' and " + colName2 + " ='" + value2 + "'";

        System.out.println(sqlQuery);
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            while (rsRows.next()) {
                rows.add(rowToColumns(rsRows));
            }
            rsRows.close();
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet executeSelect(String sqlQuery){
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);

            return rsRows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public int removeRow(String tableName, String colName, String value) {
        String sqlQuery = new String();
        sqlQuery = "Delete FROM " + tableName + " Where " + colName + " = '" + value + "'";
        try {
            return stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int removeRow(String tableName, String colName, int value) {
        String sqlQuery = new String();
        sqlQuery = "Delete FROM " + tableName + " Where " + colName + " = " + value;
        try {
            return stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addStaff(String name, String password)  {
        String sqlQuery = "INSERT INTO `staff`(`Name`, `Password`) "
                + "VALUES ('" + name + "', '" + password + "')";
        try {
            return stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public int addBooking(String date, String time, int duration, String b_reason, int ex_atendees, int l_id, int r_id, int s_id) {

        String sqlQuery = new String();
        sqlQuery = "INSERT INTO `booking` (`date`, `time`, `duration`, `booking_reason`, `expected_atendees`,`lect_id`,`room_id`,`staff_id`)"
                + " VALUES ('" + date + "', '" + time + "', " + duration + ", '" + b_reason + "', " + ex_atendees + ", " + l_id
                + "," + r_id + "," + s_id + ")";
        System.out.println(sqlQuery);
        try {
            return stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateBooking(int ID, String date, String time, int duration, String b_reason, int ex_atendees, int l_id, int r_id, int s_id) {
        String sqlQuery = new String();
        sqlQuery = "UPDATE `booking` SET `date`= '" + date
                + "' ,`time`= '" + time + "',`duration`= " + duration + ",`booking_reason`= '" + b_reason + "',`expected_atendees`= " +
                "`expected_atendees`= " + ex_atendees + "`lect_id` =" + l_id +
                " `room_id`= " + r_id + " `staff_id`= " + s_id + " WHERE `id`=" + ID + ";";
        // System.out.println(sqlQuery);
        try {
            return stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int executeUpdate(String query) {
        try {
            return stmt.executeUpdate(query);
        } catch (SQLException ex) {
            return -1;
        }
    }


    //helper
    private ArrayList<String> rowToColumns(ResultSet row) throws SQLException {
        ArrayList<String> rowData = new ArrayList<String>();
        try {
            int columnCount = row.getMetaData().getColumnCount();
            for (int j = 1; j < columnCount + 1; ++j) {
                rowData.add(row.getString(j));
            }
        } catch (SQLException e) {
            System.out.println("No Data found");
        }
        return rowData;
    }
}
