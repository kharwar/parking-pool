package Modules.Review.controller;

import Modules.Review.model.Review;
import Utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AddReviewsAndRatings {
    Statement stmt = Constants.stmt;

        private Review CreateReview() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the parking ID:");
        int parkingID = Integer.parseInt(sc.nextLine());

        System.out.println("Enter the Review:");
        String reviews = sc.nextLine();

        System.out.println("Enter the Ratings:");
        int ratings = Integer.parseInt(sc.nextLine());

        Review reviewobj = new Review(-1, Constants.loggedInUser.user_id, parkingID, reviews, ratings);
        return reviewobj;
        }
        public void AddData()
        {
            Review review = CreateReview();
            int parkingID = review.parkingID;
            String reviews = review.reviews;
            int ratings = review.ratings;
            int userID = review.userID;

            String insertQuery = "INSERT INTO reviews_and_ratings (parking_id, user_id, reviews, ratings) VALUES(" + parkingID + " , '"+userID+"' , '" + reviews + "' , '" + ratings + "');";
            try {
                stmt.executeUpdate(insertQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void displayData () throws SQLException
        {
            String query = "Select user_id, reviews, ratings from reviews_and_ratings;";

            ResultSet rs = stmt.executeQuery(query);
            try {
                while (rs.next()) {
                    System.out.println("User ID:" + rs.getInt("user_id"));
                    System.out.println("Reviews:" + rs.getString("reviews"));
                    System.out.println("Ratings:" + rs.getInt("ratings"));
                }
            } catch (Exception e) {

            }
        }
}