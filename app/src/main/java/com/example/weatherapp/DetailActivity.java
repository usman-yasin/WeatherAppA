package com.example.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.weatherapp.datastructure.Weather;

public class DetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Weather weather;
    private TextView textViewDay;
    private TextView textViewDate;
    private TextView textViewTemprature;
    private TextView textViewDescription;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialization();
        loadWeatherDetails();
        displayWeatherDetails();
    }

    private void initialization()
    {
        textViewDay = (TextView)findViewById(R.id.textViewDay);
        textViewDate = (TextView)findViewById(R.id.textViewDate);
        textViewTemprature = (TextView)findViewById(R.id.textViewTemperature);
        textViewDescription = (TextView)findViewById(R.id.textViewDescription);
    }

    private void loadWeatherDetails()
    {
        weather = (Weather)getIntent().getSerializableExtra("weather");
    }

    private void displayWeatherDetails()
    {
        if(weather!=null)
        {
            textViewDay.setText(weather.getDay());
            textViewDate.setText(weather.getDate());
            if(weather.isTempCelcius()){
                textViewTemprature.setText(weather.getTempCelcius());
            }else{
                textViewTemprature.setText(weather.getTempFahrenheit());
            }

            textViewDescription.setText(weather.getDescription());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
