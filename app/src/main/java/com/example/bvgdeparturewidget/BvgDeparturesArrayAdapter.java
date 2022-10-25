package com.example.bvgdeparturewidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bvgdeparturewidget.bvgrestapi.BvgApiUtils;
import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgDeparture;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BvgDeparturesArrayAdapter extends ArrayAdapter<BvgDeparture> implements Filterable {
    private final List<BvgDeparture> listItemsData;
    private List<BvgDeparture> listItemsShow;

    public BvgDeparturesArrayAdapter(Context context, List<BvgDeparture> items) {
        super(context, 0, items);
        listItemsData = items;
        listItemsShow = new ArrayList<>(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_bvg_departure, parent, false);
        }

        BvgDeparture bvgDeparture = listItemsShow.get(position);

        TextView textViewDepartureName = convertView.findViewById(R.id.text_view_departure_name);
        textViewDepartureName.setText(bvgDeparture.line.name);

        TextView textViewDepartureDelay = convertView.findViewById(R.id.text_view_departure_delay);
        textViewDepartureDelay.setText(getContext().getString(R.string.bvg_line_delay,
                (bvgDeparture.delay >= 0 ? '+' : '-'), bvgDeparture.delay));
        textViewDepartureDelay.setTextColor(getContext().getColor(
                (bvgDeparture.delay > 0 ? R.color.delay_late : (bvgDeparture.delay == 0 ? R.color.no_delay : R.color.delay_early))));

        TextView textViewDepartureDestination = convertView.findViewById(R.id.text_view_departure_destination);
        textViewDepartureDestination.setText(bvgDeparture.destination.name);

        if (bvgDeparture.when != null) {
            TextView textViewDepartureTime = convertView.findViewById(R.id.text_view_departure_time);
            textViewDepartureTime.setText(BvgApiUtils.getTimeFromDate(bvgDeparture.when));
        }

        ImageView serviceIcon = convertView.findViewById(R.id.icon_departure_service);
        serviceIcon.setImageResource(BvgApiUtils.getIconIdFromBvgProduct(bvgDeparture.line.product));

        if (bvgDeparture.remarks != null && bvgDeparture.remarks.size() > 0) {
            TextView textViewDepartureTime = convertView.findViewById(R.id.text_view_remarks);
            StringBuilder sb = new StringBuilder();
            for (BvgDeparture.RemarkText remark : bvgDeparture.remarks)
                sb.append(remark.toString());
            textViewDepartureTime.setText(sb.toString());
        }


        return convertView;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listItemsShow = (List<BvgDeparture>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<BvgDeparture> filteredArrayNames = new ArrayList<>();

                String[] services = constraint.toString().toLowerCase(Locale.GERMANY).split(",");

                for (BvgDeparture departure : listItemsData) {
                    for (String s : services) {
                        if (departure.line.product.toLowerCase().contains(s)) {
                            filteredArrayNames.add(departure);
                            break;
                        }
                    }
                }

                results.count = filteredArrayNames.size();
                results.values = filteredArrayNames;

                return results;
            }
        };
    }
}
