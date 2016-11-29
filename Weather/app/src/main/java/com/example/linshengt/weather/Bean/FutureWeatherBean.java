package com.example.linshengt.weather.Bean;

/**
 * Created by linshengt on 2016/8/3.
 */
public class FutureWeatherBean {
    private String temp;
    private String weather_str;
    private String week;
    private String weather_id_fa;
    private String weather_id_fb;

    public String getWeather_id_fa() {
        return weather_id_fa;
    }

    public void setWeather_id_fa(String weather_id_fa) {
        this.weather_id_fa = weather_id_fa;
    }

    public String getWeather_id_fb() {
        return weather_id_fb;
    }

    public void setWeather_id_fb(String weather_id_fb) {
        this.weather_id_fb = weather_id_fb;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather_str() {
        return weather_str;
    }

    public void setWeather_str(String weather_str) {
        this.weather_str = weather_str;
    }

    public String getWeekk() {
        return week;
    }

    public void setWeekk(String week) {
        this.week = week;
    }
}
