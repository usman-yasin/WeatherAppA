package com.example.weatherapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.weatherapp.datastructure.Weather;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,RadioGroup.OnCheckedChangeListener {

    private ListView listViewWeather;
    private ProgressDialog progressDialog;
    private ArrayList<Weather> arrayListWeather;
    private RadioGroup radioGroup;
    private boolean isTempCelcius;
    private AdapterWeather adapterWeather;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        loadWeatherDetails();
        setupLocalNotification();
    }

    private void initialization()
    {
        listViewWeather = (ListView)findViewById(R.id.listViewWeather);
        listViewWeather.setOnItemClickListener(this);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroupTemprature);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void loadWeatherDetails()
    {
        progressDialog = ProgressDialog.show(this,"Loading","Please wait...",true,false);
        AndroidNetworking.get("http://api.openweathermap.org/data/2.5/forecast?id=524901&apikey=ff894e4a508edb82c153d29c1bf608ac")
        .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    arrayListWeather = new ArrayList<Weather>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Weather weather = new Weather(jsonArray.getJSONObject(i));
                        arrayListWeather.add(weather);
                    }

                    displayWeatherDetails();

                } catch (Exception e) {

                }
            }

            @Override
            public void onError(ANError anError) {
                progressDialog.dismiss();
            }
        });
    }

    private void displayWeatherDetails()
    {
        if(arrayListWeather!=null && arrayListWeather.size()>0)
        {
            adapterWeather = new AdapterWeather(this,R.layout.row_weather,arrayListWeather);
            listViewWeather.setAdapter(adapterWeather);
            adapterWeather.setTempCelcius(true);
            adapterWeather.notifyDataSetChanged();
        }
    }

    private void setupLocalNotification()
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 15);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        Weather weatherObj = arrayListWeather.get(position);
        weatherObj.setIsTempCelcius(isTempCelcius);
        intent.putExtra("weather",weatherObj);
        startActivityForResult(intent, Constants.ACTIVITY_DETAIL);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int id = radioGroup.getCheckedRadioButtonId();
        if(id==R.id.radioButtonCelcius){
            isTempCelcius=true;
        }else{
            isTempCelcius=false;
        }
        adapterWeather.setTempCelcius(isTempCelcius);
        adapterWeather.notifyDataSetChanged();
    }
}
