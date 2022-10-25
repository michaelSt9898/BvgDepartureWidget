package com.example.bvgdeparturewidget.bvgrestapi;

import android.util.Log;

import com.example.bvgdeparturewidget.R;
import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgProducts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BvgApiUtils {
    public static String getIbnrFromId(String id) {
        Pattern pattern = Pattern.compile("^\\w{9}$");
        for (String substring : id.split(":")) {
            Matcher matcher = pattern.matcher(substring);
            if (matcher.find()) {
                return substring;
            }
        }
        return null;
    }

    public static String getTimeFromDate(String when) {

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.GERMANY).parse(when);
            if (date == null)
                return "--:--";
            return new SimpleDateFormat("HH:mm", Locale.GERMANY).format(date);
        } catch (ParseException e) {
            Log.e("Passed date format is invalid: ", when);
        }
        return "--:--";
    }

    public static int getIconIdFromBvgProduct(String product) {
        switch (product) {
            case "suburban":
                return R.drawable.icon_sbahn;
            case "subway":
                return R.drawable.icon_ubahn;
            case "tram":
                return R.drawable.icon_tram;
            case "bus":
                return R.drawable.icon_bus;
            case "express":
                return R.drawable.icon_ice;
            case "ferry":
                return R.drawable.icon_ferry;
            case "regional":
                return R.drawable.icon_db;
            default:
                Log.e("ICON ID", "Unresolved icon name " + product);
                return R.drawable.icon_bvg;
        }
    }


    public static <T> String serializeObject(List<T> listOfObjects, String delimiter) {
        if (listOfObjects.size() == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (T item : listOfObjects) {
            sb.append(item.toString());
            sb.append(delimiter);
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> deserializeObject(String serialized, String delimiter) {
        return new ArrayList<>((Collection<T>) Arrays.asList(serialized.split(delimiter)));
    }

    public static List<String> getAvailableProducts(BvgProducts products) {
        List<String> availableProducts = new ArrayList<>();
        if (products.suburban)
            availableProducts.add("suburban");
        if (products.subway)
            availableProducts.add("subway");
        if (products.bus)
            availableProducts.add("bus");
        if (products.tram)
            availableProducts.add("tram");
        if (products.ferry)
            availableProducts.add("ferry");
        if (products.express)
            availableProducts.add("express");
        if (products.regional)
            availableProducts.add("regional");

        return availableProducts;
    }

    public static EXECUTION_STATE resolveExecutionState(int code) {
        // Code has not been written yet, command not executed yet
        if (code == -1)
            return EXECUTION_STATE.PENDING;
        if (code <= 199 && code >= 100)
            return EXECUTION_STATE.INFORMATIONAL;
        if (code <= 299 && code >= 200)
            return EXECUTION_STATE.SUCCESS;
        if (code <= 399 && code >= 300)
            return EXECUTION_STATE.REDIRECTION;
        // Everything else is failed...
        if (code <= 499 && code >= 400)
            return EXECUTION_STATE.CLIENT_ERROR;
        if (code <= 599 && code >= 500)
            return EXECUTION_STATE.SERVER_ERROR;
        // Code does not follow http-code convention
        return EXECUTION_STATE.INVALID;
    }

    public enum EXECUTION_STATE {
        PENDING,
        SERVER_ERROR,
        CLIENT_ERROR,
        REDIRECTION,
        INFORMATIONAL,
        SUCCESS,
        INVALID
    }
}
