package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeoUtils {

    private static final int EARTH_RADIUS_KM = 6371;

    private static final double SHOP_LAT = 10.0125;
    private static final double SHOP_LON = 105.7324;

    private static final int DELIVERY_SPEED = 50;

    private static final int WAIT_APPROVAL = 15;
    private static final int WAIT_PICKUP = 20;
    private static final int WAIT_DELIVERY = 10;
    private static final int WAIT_RECEIVER = 5;

    public static double estimateDeliveryTimeFromLocation(String location) {
        double[] coor = getCoordinates(location);
        if (coor != null) {
            return calculateDeliveryTime(calculateDistanceFromFUCT(coor[0], coor[1]));
        } else {
            return 60;
        }
    }

    private static double[] getCoordinates(String location) {
        try {
            String urlStr = "https://nominatim.openstreetmap.org/search?format=json&q=" + location.replace(" ", "%20");
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject obj = jsonArray.getJSONObject(0);
                double lat = obj.getDouble("lat");
                double lon = obj.getDouble("lon");
                return new double[]{lat, lon};
            }
        } catch (Exception e) {
        }
        return null;
    }

    private static double calculateDistanceFromFUCT(double toLat, double toLon) {
        double dLat = Math.toRadians(toLat - SHOP_LAT);
        double dLon = Math.toRadians(toLon - SHOP_LON);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(SHOP_LAT)) * Math.cos(Math.toRadians(toLat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    private static int calculateDeliveryTime(double distance) {
        int travelTime = (int) Math.ceil((distance / DELIVERY_SPEED) * 60);
        int totalTime = WAIT_APPROVAL + WAIT_PICKUP + travelTime + WAIT_DELIVERY + WAIT_RECEIVER;
        return totalTime;
    }

    public static void main(String[] args) {
        System.out.println(estimateDeliveryTimeFromLocation("xa tan trung, huyen phu tan, tinh an giang"));
    }
}
