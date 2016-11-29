package com.example.linshengt.weather.Bean;

import java.util.List;

/**
 * Created by linshengt on 2016/8/1.
 */
public class WeatherBean {
    private String id;
    private String windDirection;
    private String windStrenth;
    private String humdity;
    private List<FutureWeatherBean>  futureWeatherBeanList;
    private List<Future24HWeatherBean>  future24HWeatherBeanList;
    private String city;
    private String weather_str;
    private String now_temp;
    private String tempHL;

    public String getTempHL() {
        return tempHL;
    }

    public void setTempHL(String tempHL) {
        this.tempHL = tempHL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindStrenth() {
        return windStrenth;
    }

    public void setWindStrenth(String windStrenth) {
        this.windStrenth = windStrenth;
    }

    public String getHumdity() {
        return humdity;
    }

    public void setHumdity(String humdity) {
        this.humdity = humdity;
    }

    public List<Future24HWeatherBean> getFuture24HWeatherBeanList() {
        return future24HWeatherBeanList;
    }

    public void setFuture24HWeatherBeanList(List<Future24HWeatherBean> future24HWeatherBeanList) {
        this.future24HWeatherBeanList = future24HWeatherBeanList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather_str() {
        return weather_str;
    }

    public void setWeather_str(String weather_str) {
        this.weather_str = weather_str;
    }

    public String getNow_temp() {
        return now_temp;
    }

    public void setNow_temp(String now_temp) {
        this.now_temp = now_temp;
    }

    public List<FutureWeatherBean> getFutureWeatherBeanList() {
        return futureWeatherBeanList;
    }

    public void setFutureWeatherBeanList(List<FutureWeatherBean> futureWeatherBeanList) {
        this.futureWeatherBeanList = futureWeatherBeanList;
    }
}
