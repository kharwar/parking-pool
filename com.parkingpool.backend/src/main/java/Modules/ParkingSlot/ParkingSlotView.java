package Modules.ParkingSlot;

import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.controller.AddParkingSlot;
import Modules.ParkingSlot.controller.DeleteParkingSlot;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Modules.ParkingSlot.model.ParkingSlot;
import Modules.User.model.USER_TYPE;
import Modules.User.model.User;
import Utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ParkingSlotView {
    User loggedInUser;
    Scanner sc = Constants.sc;
    Statement stmt = Constants.stmt;
    ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO = ParkingSlotQueryBuilder.getInstance();

    // ----- PUBLIC ITEMS -----
    public ParkingSlotView(User user){
        loggedInUser = user;
        Constants.setParkingSlotQueryBuilderDao(parkingSlotQueryBuilderDAO);
    }

    public boolean displayParkingSlotMenu(){
        boolean toContinue = true;
        switch (loggedInUser.role){
            case VENDOR:
                toContinue = displayVendorMenu();
                break;
            case CUSTOMER:
                System.out.println("Customer menu accessed.");
                toContinue = true;
                break;
            case ADMIN:
                System.out.println("Admin menu accessed");
                toContinue = true;
                break;
            default:
                System.out.println("User role not recognized");
                System.exit(0);
        }
        return toContinue;
    }



    // ----- PRIVATE ITEMS -----
    // ----- For displaying Vendor specific menu -----
    private boolean displayVendorMenu(){
        Constants.printAndSpeak("Enter the following numbers to access the corresponding item:\n1: Add Parking Slot.\n2: View My Parking Slots.\n3: Exit ParkingPool.\nEnter your command: ");
        boolean toContinue = true;
        int input = Integer.parseInt(sc.nextLine());
        switch (input){
            case 1:
                AddParkingSlot addParkingSlot = new AddParkingSlot(parkingSlotQueryBuilderDAO);
                addParkingSlot.AddParkingSlots(addParkingSlotDetails());
                toContinue = true;
                break;
            case 2:
                try {
                    displayMyParkingSlots();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                toContinue = true;
                break;
            case 3:
                Constants.printAndSpeak("See you soon!");
                toContinue = false;
                break;
            default:
                Constants.printAndSpeak("Incorrect input.");
                displayVendorMenu();
                break;
        }
        return toContinue;
    }

    private void displayMyParkingSlots() throws SQLException {
        if(loggedInUser.role != USER_TYPE.VENDOR){
            Constants.printAndSpeak("User is not a Vendor!");
        } else {
            String myParkingSlotsQuery = "SELECT * from ParkingSlot where owner_user_id="+loggedInUser.user_id;
            ResultSet myParkingSlotsResultSet = stmt.executeQuery(myParkingSlotsQuery);
            ArrayList<ParkingSlot> myParkingSlots = ParkingSlotUtils.ResultSetToParkingSlot(myParkingSlotsResultSet);
            for (int i = 0; i < myParkingSlots.size(); i++) {
                ParkingSlot myParkingSlot = myParkingSlots.get(i);
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Parking Slot ID: " + myParkingSlot.parking_slot_id);
                System.out.println("Distance from Elevator (0 if no elevator): " + myParkingSlot.distance_from_elevator);
                System.out.println("Address: " + myParkingSlot.address);
                System.out.println("If the Parking is on Street: " + (myParkingSlot.is_on_street == 1 ? "Yes" : "No"));
                System.out.println("If the Parking is for handicap: " + (myParkingSlot.is_handicap == 1 ? "Yes" : "No"));
                System.out.println("Hourly Rate: " + myParkingSlot.hourly_rate);
                System.out.println("Longitude: " + myParkingSlot.longitude);
                System.out.println("Latitude: " + myParkingSlot.latitude);
                System.out.println("-------------------------------------------------------------------------");
            };
            int selectedItem  = displayEditParkingSlotMenu();
            switch(selectedItem){
                case 1:
                    DeleteParkingSlot deleteParkingSlot = new DeleteParkingSlot(parkingSlotQueryBuilderDAO);
                    Constants.printAndSpeak("Enter the Parking Slot ID you want to delete: ");
                    deleteParkingSlot.deleteParkingSlot(Integer.parseInt(sc.nextLine()), loggedInUser.user_id);
                    break;
                case 2:
                    //TODO
                    System.out.println("Modify Parking Slot accessed");
                    break;
                case 3:
                    displayVendorMenu();
                    break;
                default:
                    Constants.printAndSpeak("Selected option not recognized.");
                    displayVendorMenu();
                    break;
            }
        }
    }

    private int displayEditParkingSlotMenu(){
        Constants.printAndSpeak("** Edit Parking Slots Menu **\n1. Delete a Parking Slot.\n2. Modify a Parking Slot.\n3. Go back\nEnter your command: ");
        return Integer.parseInt(sc.nextLine().trim());
    }

    // This method will fetch all the parking slot details from the user and return a ParkingSlot.
    private ParkingSlot addParkingSlotDetails(){
        System.out.print("Enter the address of the parking slot: ");
        String address = sc.nextLine().trim();

        System.out.print("\nEnter the distance from elevator(enter 0 if there is no elevator): ");
        int distance_from_elevator = Integer.parseInt(sc.nextLine().trim());

        System.out.print("\nIs it for handicap? Yes or No: ");
        int is_handicap = Integer.parseInt(String.valueOf(sc.nextLine().toUpperCase().equals("Y") ? '1' : '0'));

        System.out.print("\nEnter the longitude of the parking slot: ");
        double longitude = Double.parseDouble(sc.nextLine().trim());

        System.out.print("\nEnter the latitude of the parking slot: ");
        double latitude = Double.parseDouble(sc.nextLine().trim());

        System.out.print("\nIs the parking on street? Yes or No: ");
        int is_on_street = Integer.parseInt(String.valueOf(sc.nextLine().toUpperCase().equals("Y") ? '1' : '0'));

        System.out.print("\nEnter the hourly rate of the parking slot: ");
        float hourly_rate = Float.parseFloat(sc.nextLine().trim());

        int owner_user_id = loggedInUser.user_id;

        ParkingSlot parkingSlot = new ParkingSlot(-1, distance_from_elevator, address, is_handicap, longitude, latitude, hourly_rate, is_on_street, owner_user_id);
        return parkingSlot;
    }
}
