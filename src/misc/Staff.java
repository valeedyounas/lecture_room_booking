package misc;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import database.MySQLDatabase;

public class Staff implements Serializable {
    private static MySQLDatabase db = MySQLDatabase.getInstance();
    private String name;
    private int id;
    private String password;

    public static boolean singUp(Staff s) {
        if (db.addStaff(s.getName(), s.getPassword()) > 0)
            return true;

        return false;
    }

    public static boolean verify(int userID, String password) {
        ArrayList<ArrayList<String>> staff = db.getIndexValue("Staff", "id", userID);

        int ID = Integer.parseInt(staff.get(0).get(0));
        String pw = staff.get(0).get(2);
        System.out.println(ID + pw);
        if (ID == userID && pw.contentEquals(password)) {

            return true;
        }

        return false;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
