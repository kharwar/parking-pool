package Modules.ParkingSlot.model;

import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;

public class ParkingSlot {
    public int parking_slot_id;
    public int distance_from_elevator;
    public String address;
    public int is_handicap;
    public double longitude;
    public double latitude;
    public double hourly_rate;
    public int is_on_street;
    public int owner_user_id;

    public ParkingSlot(int parking_slot_id,
                       int distance_from_elevator,
                       String address,
                       int is_handicap,
                       double longitude,
                       double latitude,
                       double hourly_rate,
                       int is_on_street,
                       int owner_user_id){
        this.parking_slot_id = parking_slot_id;
        this.distance_from_elevator = distance_from_elevator;
        this.address = address;
        this.is_handicap = is_handicap;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hourly_rate = hourly_rate;
        this.is_on_street = is_on_street;
        this.owner_user_id = owner_user_id;

    }
}
