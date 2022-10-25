package com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses;

import androidx.annotation.NonNull;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.bvgapinestedclasses.BvgCoordinates;
import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.bvgapinestedclasses.BvgLine;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BvgDeparture extends BvgApiResponseObject {
    @SerializedName("tripId")
    public String tripId;
    @SerializedName("stop")
    public BvgLocation stop;
    @SerializedName("when")
    public String when;
    @SerializedName("plannedWhen")
    public String plannedWhen;
    @SerializedName("delay")
    public int delay;
    @SerializedName("platform")
    public int platform;
    @SerializedName("plannedPlatform")
    public int plannedPlatform;
    @SerializedName("prognosisType")
    public String prognosisType;
    @SerializedName("direction")
    public String direction;
    @SerializedName("provenance")
    public String provenance;
    @SerializedName("line")
    public BvgLine line;
    @SerializedName("remarks")
    public List<RemarkText> remarks;
    @SerializedName("origin")
    public String origin;
    @SerializedName("destination")
    public BvgLocation destination;
    @SerializedName("currentTripPosition")
    public BvgCoordinates currentTripPosition;
    @SerializedName("occupancy")
    public String occupancy;

    public static class RemarkText {
        @SerializedName("type")
        public String type;
        @SerializedName("text")
        public String text;

        @NonNull
        public String toString() {
            return type + " " + text;
        }
    }

    private static class RemarkHint extends RemarkText {
        @SerializedName("code")
        public String code;

        @NonNull
        public String toString() {
            return code + " " + super.toString();
        }
    }

    private static class RemarkWarning extends RemarkText {
        @SerializedName("id")
        public int id;
        @SerializedName("summary")
        public String summary;
        @SerializedName("icon")
        public Icon icon;
        @SerializedName("priority")
        public int priority;
        @SerializedName("products")
        public BvgProducts products;
        @SerializedName("company")
        public String company;
        @SerializedName("categories")
        public List<Integer> categories;
        @SerializedName("validFrom")
        public String validFrom;
        @SerializedName("validUntil")
        public String validUntil;
        @SerializedName("modified")
        public String modified;

        @NonNull
        public String toString() {
            return "W " + summary + " " + super.toString();
        }

        private static class Icon {
            @SerializedName("icon")
            public String icon;
            @SerializedName("title")
            public String title;
        }
    }
}
