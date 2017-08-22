package weather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
    private static String APP_ID = "f335ba0748d8f9f54d40704ae65034e1";

    public static String getWeather(String city, String country) throws IOException {

        // don't actually need country, might be good to leave it as param in case of new implementation
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + APP_ID);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        JSONObject json = new JSONObject(content.toString());
        int temp = (int) (json.getJSONObject("main").getDouble("temp") - 273.15);

        return temp + "C";
    }
}
