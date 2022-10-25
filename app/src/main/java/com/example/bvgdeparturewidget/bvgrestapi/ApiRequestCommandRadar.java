package com.example.bvgdeparturewidget.bvgrestapi;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgStop;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class ApiRequestCommandRadar extends ApiRequestCommand {

    // Key for required argument
    private static final String KEY_NORTH = "north";
    private static final String KEY_SOUTH = "south";
    private static final String KEY_EAST = "east";
    private static final String KEY_WEST = "west";

    // optional Key set for argument map
    private static final String KEY_N_RESULTS = "results";
    private static final String KEY_FRAMES = "frames";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_POLYLINES = "polylines";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_PRETTY = "pretty";

    public ApiRequestCommandRadar(ApiRequestCommandRadarBuilder builder) {
        this.buildUri("radar?", builder.arguments);
    }

    public BvgStop[] parseResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, BvgStop[].class);
    }

    public static class ApiRequestCommandRadarBuilder {
        // required + optional arguments
        private final Map<String, String> arguments = new HashMap<>();

        public ApiRequestCommandRadarBuilder(double _north, double _south, double _west, double _east) {
            arguments.put(KEY_NORTH, String.valueOf(_north));
            arguments.put(KEY_SOUTH, String.valueOf(_south));
            arguments.put(KEY_EAST, String.valueOf(_east));
            arguments.put(KEY_WEST, String.valueOf(_west));
        }

        public ApiRequestCommandRadarBuilder setResults(int _results) {
            arguments.put(KEY_N_RESULTS, String.valueOf(_results));
            return this;
        }

        public ApiRequestCommandRadarBuilder setFrames(int _frames) {
            arguments.put(KEY_FRAMES, String.valueOf(_frames));
            return this;
        }

        public ApiRequestCommandRadarBuilder setDuration(int _duration) {
            arguments.put(KEY_DURATION, String.valueOf(_duration));
            return this;
        }

        public ApiRequestCommandRadarBuilder setPolylines(boolean _polylines) {
            arguments.put(KEY_POLYLINES, String.valueOf(_polylines));
            return this;
        }

        public ApiRequestCommandRadarBuilder setLanguage(String _language) {
            arguments.put(KEY_LANGUAGE, String.valueOf(_language));
            return this;
        }

        public ApiRequestCommandRadarBuilder setPretty(boolean _pretty) {
            arguments.put(KEY_PRETTY, String.valueOf(_pretty));
            return this;
        }
    }
}
