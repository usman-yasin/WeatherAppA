package com.example.weatherapp.datastructure;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Shariq on 19/01/2017.
 */

public class Weather implements Serializable{
    private String temp="";
    private String tempMin = "";
    private String tempMax = "";
    private String humidity = "";
    private String description = "";
    private String date = "";
    private String day = "";
    private String tempCelcius = "";
    private String tempFahrenheit = "";
    private boolean isTempCelcius;


    public Weather(JSONObject jsonObject) {

        try {

            if(jsonObject.has("dt_txt"))
            {
                setDate(jsonObject.getString("dt_txt"));
            }

            if(jsonObject.has("weather"))
            {
                String desc = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                setDescription(desc);
            }
            JSONObject mainObj = jsonObject.getJSONObject("main");
            if(mainObj.has("temp")){
                setTemp(mainObj.getString("temp"));
            }
            if(mainObj.has("temp_min")){
                setTempMin(mainObj.getString("temp_min"));
            }
            if(mainObj.has("temp_max")){
                setTempMax(mainObj.getString("temp_max"));
            }
            if(mainObj.has("humidity")){
                setHumidity(mainObj.getString("humidity"));
            }

            SimpleDateFormat dateFormatServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormatLocal = new SimpleDateFormat("EEEE");
            String day = dateFormatLocal.format(dateFormatServer.parse(getDate()));
            setDay(day);

            dateFormatLocal = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss a");
            date = dateFormatLocal.format(dateFormatServer.parse(getDate()));
            setDate(date);

            setTempFahrenheit(convertKelvinToFahrenheit(temp));
            setTempCelcius(convertFahrenheitToCelcius(getTempFahrenheit()));

        } catch (Exception exc) {
            int h = 0;

        }

        //
    }


    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String convertKelvinToFahrenheit(String kelvin) {
        double k = Double.parseDouble(kelvin);
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(k * 9/5 - 459.67) + "";
    }
    public String convertFahrenheitToCelcius(String fahrenheit) {
        double f = Double.parseDouble(fahrenheit);
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format((f - 32) / 1.8) + "";
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTempCelcius() {
        return tempCelcius;
    }

    public void setTempCelcius(String tempCelcius) {
        this.tempCelcius = tempCelcius;
    }

    public String getTempFahrenheit() {
        return tempFahrenheit;
    }

    public void setTempFahrenheit(String tempFahrenheit) {
        this.tempFahrenheit = tempFahrenheit;
    }

    public boolean isTempCelcius() {
        return isTempCelcius;
    }

    public void setIsTempCelcius(boolean isTempCelcius) {
        this.isTempCelcius = isTempCelcius;
    }
}
