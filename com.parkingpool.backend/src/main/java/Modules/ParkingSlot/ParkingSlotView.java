package Modules.ParkingSlot;

import Modules.Analytics.AnalyticsView;
import Modules.ParkingSlot.Utils.ParkingSlotUtils;
import Modules.ParkingSlot.controller.AddParkingSlot;
import Modules.ParkingSlot.controller.DeleteParkingSlot;
import Modules.ParkingSlot.controller.FindParkingSlots;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Modules.ParkingSlot.model.ParkingSlot;
import Modules.User.model.USER_TYPE;
import Modules.User.model.User;
import Utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

    public boolean displayParkingSlotMenu() throws SQLException, ParseException {
        boolean toContinue = true;
        switch (loggedInUser.role){
            case VENDOR:
                toContinue = displayVendorMenu();
                break;
            case CUSTOMER:
                toContinue = displayCustomerMenu();
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

    //----- For displaying Customer specific menu -----
    public boolean displayCustomerMenu() throws SQLException, ParseException {
        Constants.printAndSpeak("Enter the following numbers to access the corresponding item:\n1: Book a Parking Slot.\n2. View My Bookings\n3: Exit ParkingPool.\nEnter your command: ");
        boolean toContinue = true;
        int input =  Integer.parseInt(sc.nextLine());
        switch(input){
            case 1:
                FindParkingSlots findParkingSlots = new FindParkingSlots();
                Constants.printAndSpeak("Enter Longitude: ");
                double longitude = Double.parseDouble(sc.nextLine());
                Constants.printAndSpeak("Enter Latitude: ");
                double latitude = Double.parseDouble(sc.nextLine());
                Constants.printAndSpeak("Enter the date (dd/MM/yyyy) you want to find the Parking Slot for: ");
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
                Constants.printAndSpeak("Enter the time (hh:mm:ss) you want to book your slot for: ");
                LocalTime startTime = LocalTime.parse(sc.nextLine());
                Constants.printAndSpeak("Enter the time (hh:mm:ss) you want to end your booking: ");
                LocalTime endTime = LocalTime.parse(sc.nextLine());
                ArrayList<ParkingSlot> foundParkingSlots = findParkingSlots.findAvailableParkingSlots(longitude, latitude, date, startTime, endTime);
                ParkingSlotUtils.viewParkingSlots(foundParkingSlots);
                Constants.printAndSpeak("Enter the following numbers to access the corresponding item: \n1. Book a Parking Slot from above\n2. Filter according to rate\n3. Go back");
                int bookInput = Integer.parseInt(sc.nextLine());

                switch (bookInput){
                    case 1:
                        //TODO: FOR BHAVNA (ADD BookAParkingSlot HERE)
                        break;
                    case 2:
                        ArrayList<ParkingSlot> sortedParkingSlots = findParkingSlots.filterAccordingToRate(foundParkingSlots);
                        ParkingSlotUtils.viewParkingSlots(sortedParkingSlots);
                        break;
                    default:
                        Constants.printAndSpeak("Incorrect input.");
                        break;
                }
                break;
            case 2:
                // TODO: FOR BHAVNA (ViewMyBookings)
                break;
            case 3:
                Constants.printAndSpeak("See you soon!");
                System.exit(0);
                break;
        }
        return toContinue;
    }

    // ----- For displaying Admin specific Menu
    private boolean displayAdminMenu() throws SQLException {
        AnalyticsView analyticsView = new AnalyticsView();
        boolean toContinue = true;
        Constants.printAndSpeak("Enter the following number to access the corresponding item:\n1. Show Analysis of the data for ParkingPool\n2. Generate a Spreadsheet for the analytics\n3. Exit ParkingPool");
        int input = Integer.parseInt(sc.nextLine());
        switch(input){
            case 1:
                analyticsView.showAnalytics();
                toContinue = true;
                break;
            case 2:
                analyticsView.createAnalyticsCSV();
                toContinue = true;
                break;
            case 3:
                Constants.printAndSpeak("See you soon!");
                toContinue = false;
                break;
            default:
                Constants.printAndSpeak("Unknown item accessed! Try again!");
                toContinue = true;
                break;
        }
        return toContinue;
    }

    //Displays All Parking Slots of the loggedIn User.
    private void displayMyParkingSlots() throws SQLException {
        if(loggedInUser.role != USER_TYPE.VENDOR){
            Constants.printAndSpeak("User is not a Vendor!");
        } else {
            String myParkingSlotsQuery = "SELECT * from ParkingSlot where owner_user_id="+loggedInUser.user_id;
            ResultSet myParkingSlotsResultSet = stmt.executeQuery(myParkingSlotsQuery);
            ArrayList<ParkingSlot> myParkingSlots = ParkingSlotUtils.ResultSetToParkingSlot(myParkingSlotsResultSet);
            ParkingSlotUtils.viewParkingSlots(myParkingSlots);
            int selectedItem  = displayEditParkingSlotMenu();
            switch(selectedItem){
                case 1:
                    DeleteParkingSlot deleteParkingSlot = new DeleteParkingSlot(parkingSlotQueryBuilderDAO);
                    Constants.printAndSpeak("Enter the Parking Slot ID you want to delete: ");
                    deleteParkingSlot.deleteParkingSlot(Integer.parseInt(sc.nextLine()), loggedInUser.user_id);
                    break;
                case 2:
                    displayVendorMenu();
                    break;
                default:
                    Constants.printAndSpeak("Selected option not recognized.");
                    displayVendorMenu();
                    break;
            }
        }
    }

    //Displays Edit Parking Slot Menu of the logged in user
    private int displayEditParkingSlotMenu(){
        Constants.printAndSpeak("** Edit Parking Slots Menu **\n1. Delete a Parking Slot.\n2. Go back\nEnter your command: ");
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

    private void BookAParkingSlot(int parking_Id, Date date, LocalTime start_time, LocalTime end_time){
        //TODO: FOR BHAVNA
    }
}
