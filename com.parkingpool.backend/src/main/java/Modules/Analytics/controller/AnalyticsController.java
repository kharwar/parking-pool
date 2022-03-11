package Modules.Analytics.controller;

import Modules.Analytics.model.AnalyticsData;
import Utils.Constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import com.opencsv.CSVWriter;
import java.io.*;


import java.util.ArrayList;

public class AnalyticsController {



    public void createAnalyticsInCSVFormat(String FolderPath) throws SQLException {


        ArrayList<AnalyticsData> l = this.getAnalytics();

        String filePath = FolderPath+"\\Analytics.csv";
        File file = new File(filePath);
        try {

            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Parking_slot_id", "Address", "Location_link","Revenue_generated","Total_Hours_of_use" };
            writer.writeNext(header);

            for(AnalyticsData ad: l)
            {
                String[] data1 = {String.valueOf(ad.parking_slot_id), ad.address, "https://www.google.com/maps/@"+ad.longitude+","+ad.latitude+",15z", String.valueOf(ad.revenue_generated), String.valueOf(ad.total_hours)};
                writer.writeNext(data1);
            }

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


    public ArrayList<AnalyticsData> getAnalytics() throws SQLException {
        ArrayList<AnalyticsData>  a_list = new ArrayList<AnalyticsData>();

        String query = "SELECT * FROM ParkingSlot;";

        Statement st = Constants.stmt;

        ResultSet rs1 = st.executeQuery(query);


        while(rs1.next())
        {
            AnalyticsData d = new AnalyticsData();
            //System.out.println(rs1.getInt("id"));

            d.setParking_slot_id(rs1.getInt("id"));
            d.setLatitude(rs1.getDouble("latitude"));
            d.setLongitude(rs1.getDouble("longitude"));
            d.setAddress(rs1.getString("address"));


            double totalHours = getTotalHours(rs1);

            d.setTotal_hours(totalHours);
            d.setRevenue_generated(totalHours*rs1.getDouble("hourly_rate"));

            a_list.add(d);
        }
        //st.close();
        return a_list;
    }

    private double getTotalHours(ResultSet rs1) throws SQLException {
        double totalHours = 0;
        String q = "SELECT * FROM booking where parking_id="+ rs1.getInt("id")+";";
        Connection conn = Constants.conn;
        Statement st2 = conn.createStatement();
        ResultSet rs2 = st2.executeQuery(q);
        while(rs2.next())
        {
            totalHours += getTimeLength(rs2.getTime("start_time"),rs2.getTime("end_time"));
        }
        return totalHours;
    }


    private double getTimeLength(Time start_time, Time end_time)
    {
        return (end_time.getHours()-start_time.getHours())+((double)(end_time.getMinutes()-start_time.getMinutes())/60)+((double)(end_time.getSeconds()-start_time.getSeconds())/3600);
    }

}
