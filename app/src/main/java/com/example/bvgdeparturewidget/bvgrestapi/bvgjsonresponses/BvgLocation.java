package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.bvgapinestedclasses.BvgCoordinates;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BvgLocation extends BvgNamedApiResponseObject {
    @SerializedName("location")
    public Location location;
    @SerializedName("stops")
    public List<BvgLocation> stops;
    @SerializedName("products")
    public BvgProducts products;
    @SerializedName("stationDHID")
    public String stationDHID;

    private static class Location extends BvgCoordinates {
        @SerializedName("id")
        public String id;
    }
}
