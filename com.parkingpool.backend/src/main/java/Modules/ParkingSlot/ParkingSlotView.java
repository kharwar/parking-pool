package Modules.ParkingSlot;

import Modules.ParkingSlot.controller.AddParkingSlot;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Modules.ParkingSlot.model.ParkingSlot;
import Modules.User.model.USER_TYPE;
import Modules.User.model.User;
import Utils.Constants;

import java.util.Locale;
import java.util.Scanner;

public class ParkingSlotView {
    User loggedInUser;
    Scanner sc = Constants.sc;
    ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO = ParkingSlotQueryBuilder.getInstance();

    // ----- PUBLIC ITEMS -----
    public ParkingSlotView(User user){
        loggedInUser = user;
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
        System.out.println("Enter the following numbers to access the corresponding item:");
        System.out.println("1: Add Parking Slot.\n2: Modify Parking Slot.\n3: Exit ParkingPool.");
        System.out.print("\nEnter your command: ");
        boolean toContinue = true;
        int input = Integer.parseInt(sc.nextLine());
        switch (input){
            case 1:
                AddParkingSlot addParkingSlot = new AddParkingSlot(parkingSlotQueryBuilderDAO);
                addParkingSlot.AddParkingSlots(addParkingSlotDetails());
                toContinue = true;
                break;
            case 2:
                System.out.println("Modify Parking Slot accessed.");
                toContinue = true;
                break;
            case 3:
                System.out.println("See you soon!");
                toContinue = false;
                break;
            default:
                System.out.println("Incorrect input.");
                displayVendorMenu();
                break;
        }
        return toContinue;
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
