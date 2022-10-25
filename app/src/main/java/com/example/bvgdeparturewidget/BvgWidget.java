package com.example.bvgdeparturewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.bvgdeparturewidget.bvgrestapi.ApiCommandExecutor;
import com.example.bvgdeparturewidget.bvgrestapi.ApiRequestCommandStopByID;
import com.example.bvgdeparturewidget.bvgrestapi.ApiRequestCommandStops;
import com.example.bvgdeparturewidget.bvgrestapi.BvgApiUtils;
import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgLocation;
import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgStop;

import java.util.ArrayList;
import java.util.Arrays;


public class BvgWidget extends AppWidgetProvider {

    public static final String OnRefreshButtonClick = "refreshButtonClick";
    public static final String API_REQUEST_LOG = "BVG_API_CALL";
    public static final String EXTRA_LIST_ON_CLICK_POSITION = "EXTRA_LIST_ON_CLICK_POSITION";
    public static final String EXTRA_LIST_ON_CLICK_STATION_NAME = "EXTRA_LIST_ON_CLICK_STATION_NAME";
    public static final String EXTRA_LIST_ON_CLICK_STATION_ID = "EXTRA_LIST_ON_CLICK_STATION_ID";
    private static final ArrayList<String> configuredBvgStops = new ArrayList<>();
    private final ArrayList<String> displayedStops = new ArrayList<>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        String stops = SharedPreferencesManager.getStringKey(context, ConfigurationActivity.KEY_SAVED_BVG_STOPS);
        if (stops.length() > 0) {
            String[] stopsArray = stops.split(";");
            configuredBvgStops.addAll(Arrays.asList(stopsArray));
        }


        ComponentName thisWidget = new ComponentName(context, BvgWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bvg_widget);
            remoteViews.setOnClickPendingIntent(R.id.button_refresh, getPendingSelfIntent(context, OnRefreshButtonClick));

            Intent activityIntent = new Intent(context, BvgStopDetailsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, widgetId,
                    activityIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            remoteViews.setPendingIntentTemplate(R.id.listViewBvgStops, pendingIntent);

            Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
            remoteViews.setRemoteAdapter(R.id.listViewBvgStops, intent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (OnRefreshButtonClick.equals(intent.getAction())) {
            onRefreshButtonClick(context);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    private void onRefreshButtonClick(Context context) {
        this.displayedStops.clear();
        Toast.makeText(context, "Refresh Button", Toast.LENGTH_SHORT).show();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bvg_widget);

        ApiRequestCommandStops stopsCommand = new ApiRequestCommandStops.ApiRequestCommandStopBuilder("Schöneweide")
                .setCompletion(true)
                .setNrResults(3)
                .setIncludeFuzzyMatches(true)
                .build();

        ApiCommandExecutor commandExecutor = new ApiCommandExecutor(response -> {
            BvgStop[] stops = stopsCommand.parseResponse(response);
            for (BvgStop stop : stops) {
                String ib_nr = BvgApiUtils.getIbnrFromId(stop.id);
                if (ib_nr == null) {
                    Log.e(API_REQUEST_LOG, "Skip " + stop.id + ", could no resolve ib_nr");
                    continue;
                }
                ApiRequestCommandStopByID stopsByIDCommand = new ApiRequestCommandStopByID.ApiRequestCommandStopByIDBuilder(ib_nr)
                        .setLanguage("de")
                        .setLinesOfStop(true)
                        .setPretty(false)
                        .build();
                ApiCommandExecutor commandExecutorInner = new ApiCommandExecutor(response1 -> {
                    BvgLocation bvgLocation = stopsByIDCommand.parseResponse(response1);
                    displayedStops.add(bvgLocation.name);
                    AppWidgetManager.getInstance(context).updateAppWidget(
                            new ComponentName(context, BvgWidget.class), remoteViews);
                });
                commandExecutorInner.execute(stopsByIDCommand);
            }
            Log.i(API_REQUEST_LOG, "Parsed response " + stops.length);
        });
        commandExecutor.execute(stopsCommand);
    }
}
