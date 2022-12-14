package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements DBInterface
{

    private Connection connection = null;
    private static DBConnection dbConnection;

    final String DB_URL = "";
    final String USER="";
    final String PASS="";

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
//        Class.forName("com.mysql.jdbc.Driver");
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
