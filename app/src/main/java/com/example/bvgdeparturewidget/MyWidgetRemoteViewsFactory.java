package com.example.bvgdeparturewidget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bvgdeparturewidget.bvgrestapi.BvgLocationType;

import java.util.ArrayList;
import java.util.List;


public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final List<BvgLocationType> widgetItems = new ArrayList<>();

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        String stops = SharedPreferencesManager.getStringKey(mContext, ConfigurationActivity.KEY_SAVED_BVG_STOPS);
        if (stops.length() > 0) {
            List<BvgLocationType> stopsArray = BvgLocationType.fromSerializedString(stops);
            widgetItems.addAll(stopsArray);
        }
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION) {
            return null;
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_bvg_location);
        views.setTextViewText(R.id.tv_location_name, widgetItems.get(position).name);
        views.setImageViewResource(R.id.icon_location_type, widgetItems.get(position).iconIds.get(0));

        Bundle extras = new Bundle();
        extras.putInt(BvgWidget.EXTRA_LIST_ON_CLICK_POSITION, position);
        extras.putString(BvgWidget.EXTRA_LIST_ON_CLICK_STATION_NAME, widgetItems.get(position).name);
        extras.putString(BvgWidget.EXTRA_LIST_ON_CLICK_STATION_ID, widgetItems.get(position).locationId);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.list_item_frame, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}