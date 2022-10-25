package com.example.bvgdeparturewidget.bvgrestapi;

import android.util.Patterns;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiRequestCommand {
    private static final String API_ENDPOINT = "https://v5.bvg.transport.rest/";
    private String uri;
    private String serverResponse;

    protected ApiRequestCommand() {
    }

    void buildUri(String prefix, Map<String, String> args) {
        StringBuilder sb = new StringBuilder(API_ENDPOINT + prefix);

        for (Map.Entry<String, String> arg : args.entrySet()) {
            sb.append(String.format("%s=%s&", arg.getKey(), arg.getValue()));
        }
        this.uri = sb.substring(0, sb.length() - (args.size() > 0 ? 1 : 0));
    }

    public int run() throws IOException {
        if (uri.length() == 0) {
            throw new IOException("Build a uri before calling run()");
        }
        if (!Patterns.WEB_URL.matcher(uri).matches()) {
            throw new IOException("Uri has invalid format");
        }

        Request request = new Request.Builder()
                .url(uri)
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful())
                this.serverResponse = Objects.requireNonNull(response.body()).string();
            else {
                this.serverResponse = String.format(Locale.GERMANY, "HttpError %d (%s): %s", response.code(), BvgApiUtils.resolveExecutionState(response.code()).name(), response.message());
            }
            return response.code();
        }
    }

    public String getResponse() {
        return this.serverResponse;
    }
}
