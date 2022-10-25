package com.example.bvgdeparturewidget.bvgrestapi;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgStop;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestCommandStops extends ApiRequestCommand {

    // key for required arguments
    private static final String KEY_QUERY = "query";

    // optional Key set for argument map
    private static final String KEY_FUZZY_MATCHES = "fuzzy";
    private static final String KEY_N_RESULTS = "results";
    private static final String KEY_USE_COMPLETION = "completion";


    public ApiRequestCommandStops(ApiRequestCommandStopBuilder builder) {
        this.buildUri("stops?", builder.arguments);
    }

    public BvgStop[] parseResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, BvgStop[].class);
    }


    public static class ApiRequestCommandStopBuilder {
        // optional arguments
        private final Map<String, String> arguments = new HashMap<>();

        public ApiRequestCommandStopBuilder(String _query) {
            arguments.put(KEY_QUERY, _query);
        }

        public ApiRequestCommandStopBuilder setNrResults(int _nResults) {
            arguments.put(KEY_N_RESULTS, String.valueOf(_nResults));
            return this;
        }

        public ApiRequestCommandStopBuilder setIncludeFuzzyMatches(boolean _includeFuzzyMatches) {
            arguments.put(KEY_FUZZY_MATCHES, String.valueOf(_includeFuzzyMatches));
            return this;
        }

        public ApiRequestCommandStopBuilder setCompletion(boolean _completion) {
            arguments.put(KEY_USE_COMPLETION, String.valueOf(_completion));
            return this;
        }

        public ApiRequestCommandStops build() {
            return new ApiRequestCommandStops(this);
        }
    }
}
