package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BvgReachability extends BvgApiResponseObject {
    @SerializedName("duration")
    public int duration;
    @SerializedName("stations")
    public List<BvgLocation> stations;
}
