package com.example.bvgdeparturewidget.bvgrestapi;

import static com.example.bvgdeparturewidget.bvgrestapi.BvgApiUtils.getAvailableProducts;

import androidx.annotation.NonNull;

import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgProducts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BvgLocationType {
    private static final int LENGTH_LOCATION_ID = 12;
    public final String name;
    public final List<Integer> iconIds;
    public String locationId;

    public BvgLocationType(String _name, BvgProducts _products, String _locationId) {
        this.name = _name;
        this.iconIds = resolveTypeToIconId(_products);
        this.locationId = _locationId;
    }

    public BvgLocationType(String _name, String _locationId, List<Integer> iconIds) {
        this.name = _name;
        this.iconIds = iconIds;
        this.locationId = _locationId;
    }

    public static List<BvgLocationType> fromSerializedString(String s) {
        List<BvgLocationType> types = new ArrayList<>();
        final String delimiterIds = "::";
        for (String stop : s.split(";")) {
            if (stop.length() == 0)
                continue;

            String locationId = stop.substring(0, LENGTH_LOCATION_ID);
            int pos = stop.indexOf(delimiterIds);
            String name = stop.substring(LENGTH_LOCATION_ID, pos);
            List<Integer> ids = Arrays.stream(stop.substring(pos + delimiterIds.length()).split(delimiterIds)).map(Integer::parseInt).collect(Collectors.toList());
            types.add(new BvgLocationType(name, locationId, ids));
        }
        return types;
    }

    @NonNull
    public String toString() {
        return name;
    }

    public String serializeWithId() {
        return locationId + name + "::" + iconIds.stream().map(Object::toString).collect(Collectors.joining("::"));
    }

    public List<Integer> getIconIds() {
        return iconIds;
    }

    private List<Integer> resolveTypeToIconId(BvgProducts products) {
        List<Integer> ids = new ArrayList<>();
        if (products != null)
            for (String product : getAvailableProducts(products))
                ids.add(BvgApiUtils.getIconIdFromBvgProduct(product));

        return ids;
    }
}
