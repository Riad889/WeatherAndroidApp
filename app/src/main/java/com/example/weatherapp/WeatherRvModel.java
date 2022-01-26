package com.example.weatherapp;

import androidx.recyclerview.widget.RecyclerView;

public class WeatherRvModel {
    private String time;
    private String temp;
    private String icon;
    private String wndSc;
    public WeatherRvModel(String time, String temp, String icon, String wndSc) {
        this.time = time;
        this.temp = temp;
        this.icon = icon;
        this.wndSc = wndSc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWndSc() {
        return wndSc;
    }

    public void setWndSc(String wndSc) {
        this.wndSc = wndSc;
    }


}
