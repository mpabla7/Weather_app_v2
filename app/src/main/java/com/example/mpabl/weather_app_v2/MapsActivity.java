package com.example.mpabl.weather_app_v2;

import android.Manifest;
//import android.R;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    public static double longgg;
    public static double lattt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


        @Override
        public void onMapReady (GoogleMap googleMap) {
            mMap = googleMap;


            double uselatt = lattt;
            double uselong = longgg;

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //Toast.makeText(MapsActivity.this, "onLocationChanged called", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Log.i("Grant", "pre was NOT granted");


            } else {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

                Location lastknownlocation = mLocationManager.getLastKnownLocation(mLocationManager.GPS_PROVIDER);


                mMap.addMarker(new MarkerOptions().position(new LatLng(uselatt, uselong)).title("Marker"));
                mMap.addMarker(new MarkerOptions().position(new LatLng(uselatt, uselong)).title("Marker"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(uselatt, uselong)));
                mMap.setMyLocationEnabled(true);

            }


        }
//    public void search(View view){
//
//
//
//            Log.i("Grant", "button pressed");
//        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
//        String location = location_tf.getText().toString();
//        List<Address> addressList = null;
//
//        if(location != null || !location.equals("")){
//
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                List<Address> addresslist =  geocoder.getFromLocationName(location, 1 );
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Address address = addressList.get(0);
//            //address class stores lat and long
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//
//            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        }
//    }




    public void LatLong(double lat, double log){

         longgg = log;
         lattt = lat;

        //Log.i("plz", " " +  longgg);
        //Log.i("plz", " " +  lattt);
    }



}



