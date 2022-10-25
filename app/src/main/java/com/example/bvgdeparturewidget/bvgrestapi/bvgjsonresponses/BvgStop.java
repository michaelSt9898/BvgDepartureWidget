package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses;

import com.google.gson.annotations.SerializedName;

public class BvgStop {
    @SerializedName("id")
    public String id;
    @SerializedName("relevance")
    public double relevance;
    @SerializedName("score")
    public double score;
    @SerializedName("weight")
    public double weight;
}
