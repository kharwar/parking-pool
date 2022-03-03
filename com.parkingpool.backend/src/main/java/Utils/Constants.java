package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

public class Constants {
    public static Statement stmt;
    public static Connection conn;
    public static Scanner sc = new Scanner(System.in);
    public static void setStatement(Statement statement){
        stmt = statement;
    }
    public static void setConnection(Connection connection) { conn = connection; }
}
