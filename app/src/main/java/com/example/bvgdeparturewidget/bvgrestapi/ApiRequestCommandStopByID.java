package com.example.bvgdeparturewidget.bvgrestapi;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgLocation;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestCommandStopByID extends ApiRequestCommand {

    // optional Key set for argument map
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_LINES_OF_STOP = "results";
    private static final String KEY_PRETTY = "pretty";

    public ApiRequestCommandStopByID(ApiRequestCommandStopByIDBuilder builder) {
        this.buildUri("stops/" + builder.id + "?", builder.arguments);
    }

    public BvgLocation parseResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, BvgLocation.class);
    }


    public static class ApiRequestCommandStopByIDBuilder {
        // required
        private final String id;

        // optional arguments
        private final Map<String, String> arguments = new HashMap<>();

        public ApiRequestCommandStopByIDBuilder(String _id) {
            this.id = _id;
        }

        public ApiRequestCommandStopByIDBuilder setLinesOfStop(boolean _linesOfStop) {
            arguments.put(KEY_LINES_OF_STOP, String.valueOf(_linesOfStop));
            return this;
        }

        public ApiRequestCommandStopByIDBuilder setLanguage(String _language) {
            arguments.put(KEY_LANGUAGE, _language);
            return this;
        }

        public ApiRequestCommandStopByIDBuilder setPretty(boolean _pretty) {
            arguments.put(KEY_PRETTY, String.valueOf(_pretty));
            return this;
        }

        public ApiRequestCommandStopByID build() {
            return new ApiRequestCommandStopByID(this);
        }
    }
}
