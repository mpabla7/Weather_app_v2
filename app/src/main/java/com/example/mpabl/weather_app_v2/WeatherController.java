package com.example.mpabl.weather_app_v2;

/**
 * Created by mpabl on 7/18/2018.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class WeatherController extends AppCompatActivity {

    // Constants:
    final int REQUEST_CODE=123;
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "ce95112bcc220f08cc5f8bec97ac6b41";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    // TODO: Set LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;


    // Member Variables:
    TextView mCityLabel;
    ImageView mWeatherImage;
    //For fahrenheit
    TextView mTemperatureLabel;
    //For celisus
    TextView mTemperatureLabel2;
    //displays version number
    TextView des;
    //used to change screens
    ImageButton map0;


    // TODO: Declare a LocationManager and a LocationListener here:
    LocationManager mLocationManager; //start or stop location updates

    LocationListener mLocationListener; //notified if location is changed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Linking the elements in the layout to Java code
        mCityLabel = (TextView) findViewById(R.id.locationTV);
        mWeatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);
        mTemperatureLabel2 = (TextView) findViewById(R.id.tempTV2);
        //Change Screens... need a OnClickListener
        ImageButton changeCityButton = (ImageButton) findViewById(R.id.changeCityButton);
        //Change Screen to Map
        map0 = (ImageButton) findViewById(R.id.switchMaps);

        des = (TextView) findViewById(R.id.des);


        //Add an OnClickListener to rhe map0 button here:
        map0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use intent constructor... new Intent(where I am, where to send user when click)
                Intent myIntent = new Intent(WeatherController.this, MapsActivity.class);
                startActivity(myIntent);
            }
        });

        // TODO: Add an OnClickListener to the changeCityButton here:
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Use intent constructor... new Intent(where I am, where to send user when click)
                Intent myIntent = new Intent(WeatherController.this, ChangeCityController.class);
                startActivity(myIntent);
            }
        });
    }


    // TODO: Add onResume() here:
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Clima", "on resume called");

        Intent myIntent = getIntent();
        //Key for extra is "City"
        String city = myIntent.getStringExtra("City");

        if (city != null) {

            getWeatherForNewCity(city);

        }else {

            Log.d("Clima", "Getting weather for current location");
            getWeatherForCurrentLocation();
        }
    }


    // TODO: Add getWeatherForNewCity(String city) here:
    private void getWeatherForNewCity(String city){
        //create new RequestParams obj w/ correct keys and values
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);

        //Call letsDoSomeNetowkring()
        letsDoSomeNetworking(params);

    }


    // TODO: Add getWeatherForCurrentLocation() here:

    private void getWeatherForCurrentLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //There are four events that can trigger LocationListener
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Clima", "OnLocationChanged() callback received");

                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d("Clima", "longitude" + longitude);
                Log.d("Clima", "latitude" + latitude);


                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon",longitude);

                params.put("appid", APP_ID);
                letsDoSomeNetworking(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Clima", "OnProviderDisabled() callback received");
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Log.d("Clima", "onRequestPermissionsResult(): Permission Granted");
                getWeatherForCurrentLocation();

            }
        }else{
            Log.d("Clima", "Permission denied");
        }
    }

    // TODO: Add letsDoSomeNetworking(RequestParams params) here:
//Need to Create HTTP GET request
    private void letsDoSomeNetworking(RequestParams params){

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(WEATHER_URL, params, new JsonHttpResponseHandler(){

            //JsonHttpResponseHandler() object is for handeling the Http Response
            //was the GET request successful?

            //onSuccess called if GET request is 200
            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response) {

                //print weather data
                Log.d("Clima", "Success! JSON: " + response.toString());

                //passing the Json we got as a response to our model
                //model class worries about creating a java object by parsing the json
                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);

                updateUI(weatherData);

            }

            //onFailure called if GET request is unsuccessful
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){

                Log.d("Clima", "Fail " + e.toString());
                Log.d("Clima", "Status code " + statusCode);
                Toast.makeText(WeatherController.this, "Request Failed :(", Toast.LENGTH_LONG).show();

            }

        });

    }


    // TODO: Add updateUI() here:
    private void updateUI(WeatherDataModel weather){

        mTemperatureLabel.setText(weather.getTemperature());
        mTemperatureLabel2.setText(weather.getTemperature2());

        mCityLabel.setText(weather.getCity());

        des.setText(weather.getDescription());

        //get a paticiular weather icon's resource ID via getResources().getIdentifier()
        //getIdentifier(filename, resource folder, package name);
        int resourceID = getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());

        mWeatherImage.setImageResource(resourceID);


    }


    // TODO: Add onPause() here:

    @Override
    protected void onPause(){
        super.onPause();

        if(mLocationManager != null){
            mLocationManager.removeUpdates(mLocationListener);
        }
    }



}