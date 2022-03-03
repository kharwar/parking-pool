import DB.*;

import Modules.ParkingSlot.ParkingSlotView;
import Modules.User.UserView;
import Modules.User.model.User;
import Utils.Constants;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class ParkingPool {

    public static void main(String[] args) throws SQLException {


        Connection conn=null;//use conn for connection
        Statement stmt=null;//use stmt for statement
        PreparedStatement preparedStatement = null; //used for PreparedStatement
        DBInterface dbi = DBConnection.getInstance();
        Scanner sc= new Scanner(System.in);//use sc for scanner
        boolean IsLoggedIn=false;
        try {
            conn = dbi.getDBConnection();
            Constants.setConnection(conn);
            stmt =  conn.createStatement();
            Constants.setStatement(stmt);
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
            System.exit(0);
        }
        //start from here

        UserView uv = new UserView();

        while(!IsLoggedIn) {
            System.out.println("Please enter the numbers given below to perform the following action:\n1: SignUp\n2: Login");
            System.out.print("Enter your command: ");
            int inputt = sc.nextInt();
            if(inputt==1) {
                try {
                    uv.signUp(stmt);
                } catch (SQLException e) {
                    System.out.println("Something went wrong, can not sign you up");
                    e.printStackTrace();
                }
            }
            else if(inputt==2)
            {
                try {
                    if(uv.logIn(stmt))
                    {
                        IsLoggedIn=true;
                    }
                } catch (SQLException e) {
                    System.out.println("Something went wrong, can not sign you in");
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Please select correct input");
            }
        }

        User user = uv.getUser();
        System.out.println("\n ***** Welcome "+user.getName()+", to the ParkingPool *****\n");

        ParkingSlotView parkingSlotView = new ParkingSlotView(user);
        boolean toContinue = true;
        while(toContinue) {
            toContinue = parkingSlotView.displayParkingSlotMenu();
        }

        System.out.println("Exiting ParkingPool!");
        try {
            dbi.clearDBConnection();
        }catch (SQLException e)
        {
            System.out.println("Can not close the connection");
            e.printStackTrace();
        }

    }
}
