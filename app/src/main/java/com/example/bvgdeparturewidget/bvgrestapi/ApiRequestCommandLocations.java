package com.example.bvgdeparturewidget.bvgrestapi;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgLocation;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestCommandLocations extends ApiRequestCommand {
    // Key for required argument
    private static final String KEY_QUERY = "query";

    // optional Key set for argument map
    private static final String KEY_FUZZY_MATCHES = "fuzzy";
    private static final String KEY_N_RESULTS = "results";
    private static final String KEY_SHOW_STOPS = "stops";
    private static final String KEY_SHOW_ADDRESSES = "addresses";
    private static final String KEY_SHOW_POI = "poi";
    private static final String KEY_LINES_OF_STOP = "linesOfStop";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_PRETTY = "pretty";

    public ApiRequestCommandLocations(ApiRequestCommandLocationsBuilder builder) {
        this.buildUri("locations?", builder.arguments);
    }

    public BvgLocation[] parseResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, BvgLocation[].class);
    }

    public static class ApiRequestCommandLocationsBuilder {

        // required + optional arguments
        private final Map<String, String> arguments = new HashMap<>();

        public ApiRequestCommandLocationsBuilder(String _query) {
            arguments.put(KEY_QUERY, _query);
        }

        public ApiRequestCommandLocationsBuilder setIncludeFuzzyMatches(boolean _includeFuzzyMatches) {
            arguments.put(KEY_FUZZY_MATCHES, String.valueOf(_includeFuzzyMatches));
            return this;
        }

        public ApiRequestCommandLocationsBuilder setResults(int _results) {
            arguments.put(KEY_N_RESULTS, String.valueOf(_results));
            return this;
        }

        public ApiRequestCommandLocationsBuilder setShowStops(boolean _showStops) {
            arguments.put(KEY_SHOW_STOPS, String.valueOf(_showStops));
            return this;
        }

        public ApiRequestCommandLocationsBuilder setShowAddresses(boolean _showAddresses) {
            arguments.put(KEY_SHOW_ADDRESSES, String.valueOf(_showAddresses));
            return this;
        }

        public ApiRequestCommandLocationsBuilder setShowPoi(boolean _showPoi) {
            arguments.put(KEY_SHOW_POI, String.valueOf(_showPoi));
            return this;
        }

        public ApiRequestCommandLocationsBuilder setLinesOfStop(boolean _linesOfStop) {
            arguments.put(KEY_LINES_OF_STOP, String.valueOf(_linesOfStop));
            return this;
        }

        public ApiRequestCommandLocationsBuilder setLanguage(String _language) {
            arguments.put(KEY_LANGUAGE, _language);
            return this;
        }

        public ApiRequestCommandLocationsBuilder setPretty(boolean _pretty) {
            arguments.put(KEY_PRETTY, String.valueOf(_pretty));
            return this;
        }

        public ApiRequestCommandLocations build() {
            return new ApiRequestCommandLocations(this);
        }
    }
}
