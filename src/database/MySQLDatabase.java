package database;

import misc.Booking;
import server.Services;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
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

    public static MySQLDatabase getInstance() {
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

    public ResultSet getResultSet(String tableName) {
        String sqlQuery = "SELECT * FROM " + tableName;
        try {
            ResultSet rsRows = stmt.executeQuery(sqlQuery);
            return rsRows;
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


    public ResultSet getResultSet(String tableName, String colName, int value) {
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

    public ResultSet getResultSet(String tableName, String colName, String value) {
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

        sqlQuery = "Select * FROM " + tableName + " Where " + colName + " = " + value;

        //System.out.println(sqlQuery);
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

    public ArrayList<ArrayList<String>> getIndexValue(String tableName, String colName1, String value1, String colName2, int value2) {
        String sqlQuery = new String();
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

        sqlQuery = "Select * FROM " + tableName + " Where " + colName1 + " = '" + value1 + "' and " + colName2 + " =" + value2 + "";

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

    public ArrayList<ArrayList<String>> getIndexValue(String tableName, String colName1, String value1, String colName2, int value2,
                                                      String colName3, int value3) {
        String sqlQuery = new String();
        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

        sqlQuery = "Select * FROM " + tableName + " Where " + colName1 + " = '" + value1 + "' and " + colName2 + " =" + value2 + " and " +
                colName3 +" != " +value3;

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

    public ArrayList<ArrayList<String>> executeSelect(String sqlQuery) {

        ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
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

    /*String sqlQuery = "INSERT INTO `staff`(`Name`, `Password`) "
                   + "VALUES ('" + name + "', '" + password + "')";"*/
    public int addStaff(String name, String password) {


        String SQL_INSERT = "INSERT INTO staff (Name, Password)  VALUES (?,?)";
        try (

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lecture_room_booking", "root", "tiger");
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                        Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, name);
            statement.setString(2, password);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                // throw new SQLException("Creating user failed, no rows affected.");
                return -1;
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    // throw new SQLException("Creating user failed, no ID obtained.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public int addBooking(String date, String time, int duration, String b_reason, int ex_atendees, int l_id, int r_id, int s_id) {
        boolean check = false;
        ArrayList<Booking> bookings = Services.list_dayBookings(date, r_id);
        System.out.println("In addBooking");
        try {
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Time timeValue1 = new Time(formatter.parse(time).getTime());
            LocalTime t1 = timeValue1.toLocalTime();
            if (bookings.size() == 0)
                check = true;

            for (Booking b : bookings) {
                Time timeValue2 = new Time(formatter.parse(b.getTime()).getTime());
                LocalTime t2 = timeValue2.toLocalTime();
                // LocalTime rangeTime = t2.plusMinutes(b.getDuration());
                if (!(t1.isAfter(t2.minusMinutes(b.getDuration())) && t1.isBefore(t2.plusMinutes(b.getDuration())))) {
//                    System.out.println("Inserted time = " + t1);
//                    System.out.println("DB time = " + t2);
//                    Duration d = Duration.between(t2, t1);
//                    long minutes = Math.abs(d.toMinutes());
//                    System.out.println("Difference in time: " + minutes);
//                    System.out.println("Duration of already going class " + duration);
//                    if (minutes > duration) {
//                        check = true;
//                    }
                    check = true;
                }
            }
            if (!check) {
                return 0;
            }

            String sqlQuery = "INSERT INTO `booking` (`date`, `time`, `duration`, `booking_reason`, `expected_attendees`,`lect_id`,`room_id`,`staff_id`)"
                    + " VALUES ('" + date + "', '" + time + "', " + duration + ", '" + b_reason + "', " + ex_atendees + ", " + l_id
                    + "," + r_id + "," + s_id + ")";

          //  System.out.println(sqlQuery);
            try {
                return stmt.executeUpdate(sqlQuery);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }


    public int updateBooking(int ID, String date, String time, int duration, String b_reason, int ex_atendees, int l_id, int r_id, int s_id) {

        boolean check = false;
        ArrayList<Booking> bookings = Services.list_dayBookings(date, r_id,ID);
      //  System.out.println("In addBooking");
        try {
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Time timeValue1 = new Time(formatter.parse(time).getTime());
            LocalTime t1 = timeValue1.toLocalTime();
            if (bookings.size() == 0)
                check = true;

            for (Booking b : bookings) {
                Time timeValue2 = new Time(formatter.parse(b.getTime()).getTime());
                LocalTime t2 = timeValue2.toLocalTime();
                // LocalTime rangeTime = t2.plusMinutes(b.getDuration());
                if (!(t1.isAfter(t2.minusMinutes(b.getDuration())) && t1.isBefore(t2.plusMinutes(b.getDuration())))) {
//                    System.out.println("Inserted time = " + t1);
//                    System.out.println("DB time = " + t2);
//                    Duration d = Duration.between(t2, t1);
//                    long minutes = Math.abs(d.toMinutes());
//                    System.out.println("Difference in time: " + minutes);
//                    System.out.println("Duration of already going class " + duration);
//                    if (minutes > duration) {
//                        check = true;
//                    }
                    check = true;
                }
            }

            if (!check) {
                return 0;
            }


            String sqlQuery;
            sqlQuery = "UPDATE `booking` SET `date`= '" + date
                    + "' ,`time`= '" + time + "',`duration`= " + duration + ",`booking_reason`= '" + b_reason +
                    "', `expected_attendees`= " + ex_atendees + ",`lect_id` =" + l_id +
                    ", `room_id`= " + r_id + ", `staff_id`= " + s_id + " WHERE `id`=" + ID ;
            System.out.println(sqlQuery);
            try {
                return stmt.executeUpdate(sqlQuery);
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
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
