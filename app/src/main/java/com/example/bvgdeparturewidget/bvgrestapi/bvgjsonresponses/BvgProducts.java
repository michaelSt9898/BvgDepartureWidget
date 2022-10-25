package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses;

import com.google.gson.annotations.SerializedName;

public class BvgProducts {
    @SerializedName("id")
    public String id;
    @SerializedName("suburban")
    public boolean suburban;
    @SerializedName("subway")
    public boolean subway;
    @SerializedName("tram")
    public boolean tram;
    @SerializedName("bus")
    public boolean bus;
    @SerializedName("ferry")
    public boolean ferry;
    @SerializedName("express")
    public boolean express;
    @SerializedName("regional")
    public boolean regional;
}
