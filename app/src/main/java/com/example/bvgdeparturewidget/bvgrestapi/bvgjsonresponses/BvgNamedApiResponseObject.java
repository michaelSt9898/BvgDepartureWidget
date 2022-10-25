package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses;

import com.google.gson.annotations.SerializedName;

public class BvgNamedApiResponseObject extends BvgApiResponseObject {
    @SerializedName("type")
    public String type;
    @SerializedName("name")
    public String name;
}
