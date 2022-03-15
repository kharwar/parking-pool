package Modules.Analytics;

import Modules.Analytics.controller.AnalyticsController;
import Modules.Analytics.model.AnalyticsData;
import Modules.User.model.USER_TYPE;
import Utils.Constants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AnalyticsView {

    public AnalyticsView(){
        if(Constants.loggedInUser.role != USER_TYPE.ADMIN){
            Constants.printAndSpeak("You are not an admin. App exited forcefully!");
            System.exit(400);
        }
    }

    public void showAnalytics() throws SQLException {
        AnalyticsController ac = new AnalyticsController();

        ArrayList<AnalyticsData> l = ac.getAnalytics();

        for(AnalyticsData ad: l)
        {
            System.out.println("parking slot: "+ad.parking_slot_id);
            System.out.println("address: "+ad.address);
            System.out.println("location: "+"https://www.google.com/maps/@"+ad.longitude+","+ad.latitude+",15z");
            System.out.println("revenue generated: "+ad.revenue_generated);
            System.out.println("total hours: "+ad.total_hours);
            System.out.println();
        }
    }

    public void createAnalyticsCSV() {
        Scanner sc= new Scanner(System.in);
        AnalyticsController ac = new AnalyticsController();

        Constants.printAndSpeak("Please Enter folder Path: ");
        String path = sc.nextLine();

        try {
            ac.createAnalyticsInCSVFormat(path);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
