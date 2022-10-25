package com.example.bvgdeparturewidget.bvgrestapi;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgDeparture;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestCommandStopDepartures extends ApiRequestCommand {

    // optional Key set for argument map
    private static final String KEY_WHEN = "when";
    private static final String KEY_DIRECTION = "direction";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_LINES_OF_STOP = "linesOfStop";
    private static final String KEY_REMARKS = "remarks";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_SUBURBAN = "suburban";
    private static final String KEY_SUBWAY = "subway";
    private static final String KEY_TRAM = "tram";
    private static final String KEY_BUS = "bus";
    private static final String KEY_FERRY = "ferry";
    private static final String KEY_EXPRESS = "express";
    private static final String KEY_REGIONAL = "regional";
    private static final String KEY_PRETTY = "pretty";

    public ApiRequestCommandStopDepartures(ApiRequestCommandStopDepartures.ApiRequestCommandStopDeparturesBuilder builder) {
        this.buildUri("stops/" + builder.stationId + "/departures?", builder.arguments);
    }

    public BvgDeparture[] parseResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, BvgDeparture[].class);
    }

    public static class ApiRequestCommandStopDeparturesBuilder {
        // required
        private final String stationId;
        // optional arguments
        private final Map<String, String> arguments = new HashMap<>();

        public ApiRequestCommandStopDeparturesBuilder(String _stop) {
            this.stationId = _stop;
        }

        public ApiRequestCommandStopDeparturesBuilder setLinesOfStops(boolean _linesOfStops) {
            arguments.put(KEY_LINES_OF_STOP, String.valueOf(_linesOfStops));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setWhen(long _when) {
            arguments.put(KEY_WHEN, String.valueOf(_when));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setWhen(Date _when) {
            arguments.put(KEY_WHEN, String.valueOf(_when.getTime()));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setDirection(String _direction) {
            // optional
            arguments.put(KEY_DIRECTION, _direction);
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setDuration(int _duration) {
            arguments.put(KEY_DURATION, String.valueOf(_duration));

            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setResults(int _results) {
            arguments.put(KEY_RESULTS, String.valueOf(_results));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setRemarks(boolean _remarks) {
            arguments.put(KEY_REMARKS, String.valueOf(_remarks));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setLanguage(String _language) {
            arguments.put(KEY_LANGUAGE, _language);
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setSuburban(boolean _suburban) {
            arguments.put(KEY_SUBURBAN, String.valueOf(_suburban));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setSubway(boolean _subway) {
            arguments.put(KEY_SUBWAY, String.valueOf(_subway));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setTram(boolean _tram) {
            arguments.put(KEY_TRAM, String.valueOf(_tram));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setBus(boolean _bus) {
            arguments.put(KEY_BUS, String.valueOf(_bus));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setFerry(boolean _ferry) {
            arguments.put(KEY_FERRY, String.valueOf(_ferry));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setExpress(boolean _express) {
            arguments.put(KEY_EXPRESS, String.valueOf(_express));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setRegional(boolean _regional) {
            arguments.put(KEY_REGIONAL, String.valueOf(_regional));
            return this;
        }

        public ApiRequestCommandStopDeparturesBuilder setPretty(boolean _pretty) {
            arguments.put(KEY_PRETTY, String.valueOf(_pretty));
            return this;
        }

        public ApiRequestCommandStopDepartures build() {
            return new ApiRequestCommandStopDepartures(this);
        }
    }
}
