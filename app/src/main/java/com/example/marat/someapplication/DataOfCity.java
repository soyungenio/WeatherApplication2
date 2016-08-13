package com.example.marat.someapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marat on 23.07.2016.
 */
public class DataOfCity {
    private String city_name;
    private String city_country;
    private String city_sky;
    private String city_temp;
    private String city_press;
    private String city_humidity;
    private String city_speed;
    private String city_time;

    public DataOfCity(){}

    public DataOfCity(String stringData){
        JSONObject jsonObj;
        JSONObject main = null;
        try {
            jsonObj = new JSONObject(stringData);
            main = jsonObj.getJSONObject("main");
            ///////////name
            city_name = jsonObj.getString("name");
            //////////country
            city_country = jsonObj.getJSONObject("sys").getString("country");
            ///////////discrip
            city_sky = jsonObj.getJSONArray("weather").getJSONObject(0).getString("description");
            ////////////temp
            city_temp = String.valueOf((int)Math.round(Float.parseFloat(main.getString("temp"))));
            /////////pressure
            int press = (int)Float.parseFloat(main.getString("pressure").toString());
            press*= 0.75006;
            city_press = String.valueOf(press);
            ////////Hummidity
            city_humidity = main.getString("humidity");
            ////////wind speed
            city_speed = jsonObj.getJSONObject("wind").getString("speed");
            //////////////time
            city_time = DateFormat.getDateTimeInstance().format(new Date());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void putCity(ArrayList<String> cities){
        int count = 0;
        city_name = cities.get(count++);
        city_country = cities.get(count++);
        city_sky = cities.get(count++);
        city_temp = cities.get(count++);
        city_press = cities.get(count++);
        city_humidity = cities.get(count++);
        city_speed = cities.get(count++);
        city_time = cities.get(count++);

    }

    public String getName(){return city_name;}
    public String getCountry(){return city_country;}
    public String getSky(){return city_sky;}
    public String getTemp(){return city_temp;}
    public String getPress(){return city_press;}
    public String getHum(){return city_humidity;}
    public String getSpeed(){return city_speed;}
    public String getTime(){return city_time;}
}
