package Modules.Review.controller;

import Modules.Review.model.Review;
import Utils.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AddReviewsAndRatings {
        Statement stmt = Constants.stmt;
        public boolean addReviewAndRating(Review review) {
            int parkingId = review.parkingID;
            int userID = review.userID;
            String reviews = review.reviews;
            double ratings = review.ratings;
            String addReviewAndRatingQuery = "INSERT INTO reviews_and_ratings (parking_id, user_id, reviews, ratings) VALUES(" + parkingId + " , '"+userID+"' , '" + reviews + "' , '" + ratings + "');";
            try {
                boolean isAdded = stmt.execute(addReviewAndRatingQuery);
                if(isAdded){
                    System.out.println("Review and Rating added successfully");
                    return true;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Review and Rating not added");
                return false;
            }
        }
}