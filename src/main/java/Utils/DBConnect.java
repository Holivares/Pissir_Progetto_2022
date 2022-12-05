package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    static private final String user = "root";
    static private final String password = "";
    static private final String connString = "";

    static private DBConnect instance = null;

    private DBConnect() {
        instance = this;
    }

    public static DBConnect getInstance() {
        if (instance == null)
            return new DBConnect();
        else {
            return instance;
        }
    }

    public Connection getConnection() throws SQLException{
        try{
            //ritorna l'istanza della connection
            return DriverManager.getConnection(connString, user, password);
        }catch (SQLException e){
            throw new SQLException("Cannot get connection to" +connString, e);
        }
    }
}
