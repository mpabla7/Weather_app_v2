package com.example.mpabl.weather_app_v2;

/**
 * Created by mpabl on 7/18/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChangeCityController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //link activity to layout file
        setContentView(R.layout.change_city_layout);

        final EditText editTextField = (EditText) findViewById(R.id.queryET);
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //close ChangeCityController activity
                //screen will go awaay ChangeCityContorller will be dropped from memory
                finish();

            }
        });

        //Retreive the City name entered by the user//
        //Set a CallBack on the edit text//
        editTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //This --^^^ is triggered when the user presses enter on their keyboard

                String newCity = editTextField.getText().toString();
                //Intent navigates back to WeatherController
                Intent newCityIntent = new Intent(ChangeCityController.this, WeatherController.class);
                //package the city name into the intent as a extra (key, value) pair
                newCityIntent.putExtra("City", newCity);

                startActivity(newCityIntent);

                return false;
            }
        });

    }
}
