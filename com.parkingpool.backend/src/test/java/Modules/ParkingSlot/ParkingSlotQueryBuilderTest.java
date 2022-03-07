
package Modules.ParkingSlot;

import Modules.ParkingSlot.database.ParkingSlotQueryBuilder;
import org.junit.jupiter.api.*;

@DisplayName("ParkingSlotQueryBuilder class test suite")
public class ParkingSlotQueryBuilderTest {
    @Test
    @DisplayName("AddParkingSlotQueryBuilder Test")
    public void testAddParkingSlotQueryBuilder(){
        final int distance_from_elevator = 0;
        final String address = "6225 University Ave, Halifax, NS B3H 4R2";
        final int is_handicap = 1;
        final double longitude = 44.6374007;
        final double latitude = -63.5933855;
        final double hourly_rate = 12.95;
        final int is_on_street = 0;
        final int owner_user_id = 6;

        final ParkingSlotQueryBuilder parkingSlotQueryBuilder = ParkingSlotQueryBuilder.getInstance();
        final String actualQuery = parkingSlotQueryBuilder.AddParkingSlotQueryBuilder(distance_from_elevator, address, is_handicap, longitude, latitude, hourly_rate, is_on_street, owner_user_id);
        final String expectedQuery = "INSERT INTO ParkingSlot (address, is_handicap, longitude, latitude, hourly_rate, is_on_street, owner_user_id) VALUES('6225 University Ave, Halifax, NS B3H 4R2','1','44.6374007','-63.5933855','12.95','0','6')";
        Assertions.assertEquals(expectedQuery, actualQuery);
    }

}


