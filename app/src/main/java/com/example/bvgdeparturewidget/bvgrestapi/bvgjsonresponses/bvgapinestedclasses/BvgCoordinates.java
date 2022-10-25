package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.bvgapinestedclasses;

import com.google.gson.annotations.SerializedName;

public class BvgCoordinates {
    @SerializedName("type")
    public String type;
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("longitude")
    public double longitude;
}
