package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements DBInterface
{

    private Connection connection = null;
    private static DBConnection dbConnection;

    final String DB_URL = "jdbc:mysql://db-5308.cs.dal.ca:3306/";
    final String USER="CSCI5308_2_DEVINT_USER";
    final String PASS="phoo3saezeeGoop2";

    private DBConnection(){
        //singleton purpose
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    @Override
    public Connection getDBConnection() throws SQLException {
        clearDBConnection();
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        return connection;
    }

    @Override
    public void clearDBConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }

    }
}
