//this class is just for testing purpose

package modules.User.model;

import DB.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DBMain {

    public static void main(String args[]) throws SQLException {
        DBInterface dbi = DBConnection.getInstance();
        Connection conn = dbi.getDBConnection();
        if(conn!=null)
        {
            System.out.println("got the connection");
        }

        dbi.clearDBConnection();
    }

}
