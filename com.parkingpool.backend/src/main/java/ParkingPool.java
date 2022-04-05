import DB.DBConnection;
import DB.DBInterface;
import Modules.ParkingSlot.ParkingSlotView;
import Modules.User.UserView;
import Modules.User.model.User;
import Utils.Constants;
import Utils.Scan;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public class ParkingPool {

    public static void main(String[] args) throws SQLException, ParseException {
        Connection conn=null;
        Statement stmt=null;
        DBInterface dbi = DBConnection.getInstance();

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

        Constants.printAndSpeak("Do you want to turn the Voice Synthesizer on? Yes or No:");
        boolean synthesizerSwitch = Scan.nextLine().toUpperCase().startsWith("Y");
        Constants.toggleSynthesizer(synthesizerSwitch);

        UserView uv = new UserView();

        while(!IsLoggedIn) {
            Constants.printAndSpeak("Please enter the numbers given below to perform the following action:\n1: SignUp\n2: Login");
            Constants.printAndSpeak("Enter your command: ");
            int inputt = Integer.parseInt(Scan.nextLine());
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
