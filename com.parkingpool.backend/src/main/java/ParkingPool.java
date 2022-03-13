import DB.*;

import Modules.Analytics.AnalyticsView;
import Modules.ParkingSlot.ParkingSlotView;
import Modules.User.UserView;
import Modules.User.model.User;
import Utils.Constants;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.*;
import javax.speech.synthesis.SynthesizerModeDesc;

import java.sql.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class ParkingPool {

    public static void main(String[] args) throws SQLException, ParseException {


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
            Constants.printAndSpeak("Please enter the numbers given below to perform the following action:\n1: SignUp\n2: Login");
            Constants.printAndSpeak("Enter your command: ");
            int inputt = sc.nextInt();
            if(inputt==1) {
                try {
                    uv.signUp(stmt);
                } catch (SQLException e) {
                    Constants.printAndSpeak("Something went wrong, can not sign you up");
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
                    Constants.printAndSpeak("Something went wrong, can not sign you in");
                    e.printStackTrace();
                }
            }
            else{
                Constants.printAndSpeak("Please select correct input");
            }
        }

        User user = uv.getUser();
        Constants.setUser(user);
        Constants.printAndSpeak("\n  Welcome "+user.getName()+", to the ParkingPool \n");

        ParkingSlotView parkingSlotView = new ParkingSlotView(user);
        boolean toContinue = true;
        while(toContinue) {
            toContinue = parkingSlotView.displayParkingSlotMenu();
        }

        Constants.printAndSpeak("Exiting ParkingPool!");
        //cd System.out.println("Done");
       try {
            dbi.clearDBConnection();
        }catch (SQLException e)
        {
            Constants.printAndSpeak("Can not close the connection");
            e.printStackTrace();
        }
        System.exit(1);
    }
}
