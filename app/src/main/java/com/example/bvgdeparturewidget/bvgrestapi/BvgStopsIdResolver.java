package com.example.bvgdeparturewidget.bvgrestapi;

public class BvgStopsIdResolver {
    public final BvgStopsIdResolver.OnIdsResolvedListener callbackListener;

    public BvgStopsIdResolver(OnIdsResolvedListener _callbackListener) {
        this.callbackListener = _callbackListener;
    }

    public void resolveId(String id) {
        ApiRequestCommandStopByID stopsByIDCommand = new ApiRequestCommandStopByID.ApiRequestCommandStopByIDBuilder(id)
                .setLanguage("de")
                .setLinesOfStop(true)
                .setPretty(false)
                .build();
        ApiCommandExecutor commandExecutor = new ApiCommandExecutor(callbackListener::onResponseReceived);
    }

    public interface OnIdsResolvedListener {
        void onResponseReceived(String response);
    }
}
