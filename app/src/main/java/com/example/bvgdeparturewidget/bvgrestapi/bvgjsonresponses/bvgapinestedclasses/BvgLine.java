package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.bvgapinestedclasses;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgNamedApiResponseObject;
import com.google.gson.annotations.SerializedName;

public class BvgLine {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("fahrtNr")
    public int rideNr;
    @SerializedName("public")
    public boolean isPublic;
    @SerializedName("adminCode")
    public String adminCode;
    @SerializedName("productName")
    public String productName;
    @SerializedName("mode")
    public String mode;
    @SerializedName("product")
    public String product;
    @SerializedName("operator")
    public BvgNamedApiResponseObject operator;
    @SerializedName("symbol")
    public String symbol;
    @SerializedName("nr")
    public int number;
    @SerializedName("metro")
    public boolean metro;
    @SerializedName("express")
    public boolean express;
    @SerializedName("night")
    public boolean night;
}
