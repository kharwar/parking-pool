package Utils;

import java.sql.Statement;
import java.util.Scanner;

public class Constants {
    public static Statement stmt;
    public static Scanner sc = new Scanner(System.in);
    public static void setStatement(Statement statement){
        stmt = statement;
    }
}
