package com.example.bvgdeparturewidget.bvgrestapi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestCommandStopArrivals extends ApiRequestCommand {

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

    public ApiRequestCommandStopArrivals(ApiRequestCommandStopArrivalsBuilder builder) {
        this.buildUri(builder.stop + "/arrivals?", builder.arguments);
    }

    public static class ApiRequestCommandStopArrivalsBuilder {
        // required
        final private int stop;

        private final Map<String, String> arguments = new HashMap<>();

        public ApiRequestCommandStopArrivalsBuilder(int _stop) {
            this.stop = _stop;
        }

        public ApiRequestCommandStopArrivalsBuilder setLinesOfStops(boolean _linesOfStops) {
            arguments.put(KEY_LINES_OF_STOP, String.valueOf(_linesOfStops));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setWhen(long _when) {
            arguments.put(KEY_WHEN, String.valueOf(_when));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setWhen(Date _when) {
            arguments.put(KEY_WHEN, String.valueOf(_when.getTime()));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setDirection(String _direction) {
            // optional
            arguments.put(KEY_DIRECTION, _direction);
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setDuration(int _duration) {
            arguments.put(KEY_DURATION, String.valueOf(_duration));

            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setResults(int _results) {
            arguments.put(KEY_RESULTS, String.valueOf(_results));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setRemarks(boolean _remarks) {
            arguments.put(KEY_REMARKS, String.valueOf(_remarks));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setLanguage(String _language) {
            arguments.put(KEY_LANGUAGE, _language);
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setSuburban(boolean _suburban) {
            arguments.put(KEY_SUBURBAN, String.valueOf(_suburban));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setSubway(boolean _subway) {
            arguments.put(KEY_SUBWAY, String.valueOf(_subway));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setTram(boolean _tram) {
            arguments.put(KEY_TRAM, String.valueOf(_tram));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setBus(boolean _bus) {
            arguments.put(KEY_BUS, String.valueOf(_bus));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setFerry(boolean _ferry) {
            arguments.put(KEY_FERRY, String.valueOf(_ferry));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setExpress(boolean _express) {
            arguments.put(KEY_EXPRESS, String.valueOf(_express));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setRegional(boolean _regional) {
            arguments.put(KEY_REGIONAL, String.valueOf(_regional));
            return this;
        }

        public ApiRequestCommandStopArrivalsBuilder setPretty(boolean _pretty) {
            arguments.put(KEY_PRETTY, String.valueOf(_pretty));
            return this;
        }

        public ApiRequestCommandStopArrivals build() {
            return new ApiRequestCommandStopArrivals(this);
        }
    }
}
