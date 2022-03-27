package Utils;

import java.util.HashMap;
import java.util.Map;

public class GoogleMap {
    public static Map<String, String> parseUrl(String googleMapUrl){
        if(!googleMapUrl.contains("https://www.google.com/maps/place/")){
            Constants.printAndSpeak("ParkingPool could not detect the place from the provided URL!");
            return null;
        }
        Map<String, String> parsedData = new HashMap<String, String>();
        String[] googleMapUrlSplit = googleMapUrl.split("/");

        String address = String.join(" ", googleMapUrlSplit[5].split("\\+"));
        parsedData.put("address", address);

        String[] rawCoords = googleMapUrlSplit[6].split(",");
        String longitude = rawCoords[0].substring(1);
        String latitude = rawCoords[1];
        parsedData.put("longitude", longitude);
        parsedData.put("latitude", latitude);

        return parsedData;
    }

    public static String generateUrl(String address){
        String parsedAddress = String.join("+", address.split(" "));
        String googleMapUrl = "https://www.google.com/maps/search/?api=1&query=" + parsedAddress;
        return googleMapUrl;
    }
}
