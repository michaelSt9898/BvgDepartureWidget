package com.example.bvgdeparturewidget;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    public static final String BVG_DEPARTURES_SHARED_PREFS = "BVG_DEPARTURES_SHARED_PREFS";

    public static Object getObjectKey(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getAll().get(key);
    }

    public static int getIntKey(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static long getLongKey(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    public static String getStringKey(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static Boolean getBooleanKey(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static Float getFloatKey(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0.0f);
    }

    public static void writeKey(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void writeKey(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void writeKey(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void writeKey(Context context, String key, float value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void writeKey(Context context, String key, long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(BVG_DEPARTURES_SHARED_PREFS, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }
}
