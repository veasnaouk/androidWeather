package com.project.veasna.weather;

/**
 * Created by Neat on 6/25/17.
 */

public class Common {
    public static String API_KEY="2330a0a3fb5310a0e90f5ef6dd89aac1";
    public static String OPEN_WEATHER="http://api.openweathermap.org/data/2.5/";
    public static String FORECAST="forecast?";
    public static String WEATHER="weather?q=";
    public static String SUFFIX="&APPID="+API_KEY;


    public static String getIconURL(String ic_name){
        return new StringBuilder().append("http://openweathermap.org/img/w/").append(ic_name).append(".png").toString();
    }
    public static String getWeatherURL(String city){
        return new StringBuilder().append(OPEN_WEATHER).append(WEATHER).append(city).append(SUFFIX).toString();
    }
}
