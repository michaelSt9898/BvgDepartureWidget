package com.example.bvgdeparturewidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bvgdeparturewidget.bvgrestapi.BvgLocationType;

import java.util.ArrayList;
import java.util.List;

public class BvgLocationTypeArrayAdapter extends ArrayAdapter<BvgLocationType> {
    Context context;
    int resource, textViewResourceId;
    List<BvgLocationType> items, tempItems, suggestions;
    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return resultValue.toString();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (BvgLocationType type : tempItems) {
                    if (type.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(type);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            @SuppressWarnings("unchecked")
            List<BvgLocationType> filterList = (ArrayList<BvgLocationType>) results.values;
            if (results.count > 0) {
                clear();
                for (BvgLocationType item : filterList) {
                    add(item);
                    notifyDataSetChanged();
                }
            }
        }
    };

    public BvgLocationTypeArrayAdapter(Context context, int resource, int textViewResourceId, List<BvgLocationType> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_bvg_location, parent, false);
        }
        BvgLocationType item = items.get(position);
        if (item != null) {
            TextView locName = view.findViewById(R.id.tv_location_name);
            if (locName != null)
                locName.setText(item.toString());

            ImageView locIcon = view.findViewById(R.id.icon_location_type);
            if (locIcon != null) {
                locIcon.setTag(R.id.icon_location_type);
                locIcon.setImageResource(item.getIconIds().get(0));
            }
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}
