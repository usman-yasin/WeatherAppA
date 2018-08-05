package com.example.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.weatherapp.datastructure.Weather;

/**
 * Created by Shariq on 19/01/2017.
 */

public class AdapterWeather extends ArrayAdapter<Weather>{
    private boolean tempCelcius;
    private static class ViewHolder {
        View mainView;
        TextView textViewDate;
        TextView textViewDay;
        TextView textViewDescription;
        TextView textViewTemperature;

    }
    public Context myContext;

    private ArrayList<Weather> arrayListWeather;

    public AdapterWeather(Context context, int resource, ArrayList<Weather> objects) {
        super(context, resource, objects);
        arrayListWeather = objects;
        myContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Weather weather = arrayListWeather.get(position);
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = vi.inflate(R.layout.row_weather, null);
            viewHolder = new ViewHolder();
            viewHolder.mainView = (View)convertView.findViewById(R.id.mainView);
            viewHolder.textViewDate = (TextView)convertView.findViewById(R.id.textViewDate);
            viewHolder.textViewDay = (TextView)convertView.findViewById(R.id.textViewDay);
            viewHolder.textViewDescription = (TextView)convertView.findViewById(R.id.textViewDescription);
            viewHolder.textViewTemperature = (TextView)convertView.findViewById(R.id.textViewTemperature);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //update
        try {

            viewHolder.textViewDay.setText(weather.getDay());
            viewHolder.textViewDate.setText(weather.getDate());
            if(isTempCelcius()){
                viewHolder.textViewTemperature.setText(weather.getTempCelcius());
            }else{
                viewHolder.textViewTemperature.setText(weather.getTempFahrenheit());
            }

            viewHolder.textViewDescription.setText(weather.getDescription());

        }catch (Exception exc)
        {
            int h=0;
        }

        return convertView;
    }

    public void setTempCelcius(boolean tempCelcius){
        this.tempCelcius = tempCelcius;
    }

    public boolean isTempCelcius() {
        return this.tempCelcius;
    }

}
