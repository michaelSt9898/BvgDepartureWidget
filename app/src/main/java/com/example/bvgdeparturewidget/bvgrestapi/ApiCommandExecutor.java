package com.example.bvgdeparturewidget.bvgrestapi;

import android.os.AsyncTask;

import java.io.IOException;

public final class ApiCommandExecutor {
    public final OnApiCallbackListener callbackListener;
    private final int commandId;
    private int code = -1;

    public ApiCommandExecutor(OnApiCallbackListener listener) {
        this.callbackListener = listener;
        this.commandId = -1;
    }

    public ApiCommandExecutor(OnApiCallbackListener listener, int commandId) {
        this.callbackListener = listener;
        this.commandId = commandId;
    }

    public void execute(ApiRequestCommand command) {
        AsyncTask.execute(() -> {
            try {
                code = command.run();
                callbackListener.onResponseReceived(command.getResponse());
            } catch (IOException e) {
                callbackListener.onResponseReceived(e.getMessage());
            }
        });
    }

    public int getCommandId() {
        return commandId;
    }

    public interface OnApiCallbackListener {
        void onResponseReceived(String response);
    }
}
