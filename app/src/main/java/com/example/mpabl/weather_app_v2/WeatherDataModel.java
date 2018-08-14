package com.example.mpabl.weather_app_v2;

/**
 * Created by mpabl on 7/18/2018.
 */
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel {

    // TODO: Declare the member variables here
    private String mTemperature;
    private String mTemperature2;
    private int mCondition;
    private String mCity;
    private String mIconName;
    private String description;


    // TODO: Create a WeatherDataModel from a JSON:
    public static WeatherDataModel fromJson(JSONObject jsonObject){

        //try catach is needed if json returns a NaN or infinity
        try {
            WeatherDataModel weatherData = new WeatherDataModel();

            weatherData.mCity = jsonObject.getString("name");

            double lon = jsonObject.getJSONObject("coord").getDouble("lon");
            double lat = jsonObject.getJSONObject("coord").getDouble("lat");
            MapsActivity k = new MapsActivity();
            k.LatLong(lat,lon);


            //get value for array index 0 w/ name = id. what's id's (int) value?
            weatherData.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");

            weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);

            double tempResult = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15)*9.0/5.0 +32;
            double tempResult2 = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;

            int roundedValue = (int) Math.rint(tempResult);
            int roundedValue2 = (int) Math.rint(tempResult2);

            weatherData.mTemperature=Integer.toString(roundedValue);
            weatherData.mTemperature2=Integer.toString(roundedValue2);

            weatherData.description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

            return weatherData;

        } catch (JSONException e){
            //Print error to Logcat
            e.printStackTrace();
            return null;
        }
    }

    // TODO: Uncomment to this to get the weather image name from the condition:
    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "meme";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "meme";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }


    // TODO: Create getter methods for temperature, city, and icon name:


    public String getTemperature() {
        return mTemperature + "°F";
    }

    public String getTemperature2() {
        return mTemperature2 + "°C";
    }

    public String getCity() {
        return mCity;
    }

    public String getIconName() {
        return mIconName;
    }

    public String getDescription(){

        return description;
    }

}
