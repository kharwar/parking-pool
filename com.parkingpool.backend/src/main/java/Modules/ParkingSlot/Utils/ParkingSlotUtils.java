package Modules.ParkingSlot.Utils;

import Modules.ParkingSlot.database.ParkingSlotQueryBuilderDAO;
import Modules.ParkingSlot.model.ParkingSlot;
import Utils.Constants;

import java.sql.*;
import java.util.ArrayList;

public class ParkingSlotUtils {
    private final ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO;
    private Statement stmt = Constants.stmt;
    public ParkingSlotUtils(ParkingSlotQueryBuilderDAO parkingSlotQueryBuilderDAO) {
        this.parkingSlotQueryBuilderDAO = parkingSlotQueryBuilderDAO;
    }

    public ArrayList<ParkingSlot> FindAllParkingSlots() throws SQLException {
        String findAllParkingSlotQuery = parkingSlotQueryBuilderDAO.FindAllParkingSlotsQueryBuilder();
        ResultSet parkingSlotResultSet = stmt.executeQuery(findAllParkingSlotQuery);
        ArrayList<ParkingSlot> parkingSlots = ResultSetToParkingSlot(parkingSlotResultSet);
        return parkingSlots;
    }

    public double calculateDistanceInMeters(double lat1, double long1, double lat2,
                                            double long2) {


        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);
        return dist;
    }
    public static ArrayList<ParkingSlot> ResultSetToParkingSlot(ResultSet parkingSlotResultSet) throws SQLException {
        ArrayList<ParkingSlot> parkingSlots = new ArrayList<ParkingSlot>();
        while(parkingSlotResultSet.next()){
            parkingSlots.add(new ParkingSlot(
                    parkingSlotResultSet.getInt("id"),
                    parkingSlotResultSet.getInt("distance_from_elevator"),
                    parkingSlotResultSet.getString("address"),
                    parkingSlotResultSet.getInt("is_handicap"),
                    parkingSlotResultSet.getDouble("longitude"),
                    parkingSlotResultSet.getDouble("latitude"),
                    parkingSlotResultSet.getDouble("hourly_rate"),
                    parkingSlotResultSet.getInt("is_on_street"),
                    parkingSlotResultSet.getInt("owner_user_id")
            ));
        }
        return parkingSlots;
    }
}
