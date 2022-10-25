package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses;

import com.google.gson.annotations.SerializedName;

public class BvgApiResponseObject {
    @SerializedName("id")
    public String id;

    // Http-Get response properties
    public int code;
    public String errorMessage;
}
