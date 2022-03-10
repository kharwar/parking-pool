package Modules.Review.model;

public class Review {
    public int review_id;
    public int userID;
    public int parkingID;
    public String reviews;
    public int ratings;
    public Review(int review_id, int userID, int parkingID, String reviews, int ratings)
    {
        this.review_id = review_id;
        this.userID = userID;
        this.parkingID = parkingID;
        this.reviews = reviews;
        this.ratings = ratings;
    }
}