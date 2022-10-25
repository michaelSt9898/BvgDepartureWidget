package com.example.bvgdeparturewidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bvgdeparturewidget.bvgrestapi.ApiCommandExecutor;
import com.example.bvgdeparturewidget.bvgrestapi.ApiRequestCommandLocations;
import com.example.bvgdeparturewidget.bvgrestapi.BvgLocationType;
import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgLocation;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kotlin.text.Regex;


public class ConfigurationActivity extends AppCompatActivity {

    public static final String KEY_SAVED_BVG_STOPS = "KEY_SAVED_BVG_STOPS";
    public static final String LOG_KEY_BVG_API_RESPONSE = "LOG_KEY_BVG_API_RESPONSE";
    private final ArrayList<BvgLocationType> apiCompletedLocations = new ArrayList<>();
    private final ArrayList<BvgLocationType> addedItemsList = new ArrayList<>();
    private int appWidgetId;
    private ApiCommandExecutor bvgCommandExecutor;
    private ApiRequestCommandLocations bvgLocationsCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        setResult(Activity.RESULT_CANCELED);

        final int AUTOCOMPLETION_SUGGESTIONS = getResources().getInteger(R.integer.autocompletion_suggestions);

        // Get appwidget id that should be configured
        Bundle extras = this.getIntent().getExtras();
        appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        String stops = SharedPreferencesManager.getStringKey(getApplicationContext(), ConfigurationActivity.KEY_SAVED_BVG_STOPS);
        Regex re = new Regex("^([0-9]{12}[\\p{L} .+\\-\\(\\)]*(::[0-9]{10})*;)*([0-9]{12}[\\p{L} .+\\-\\(\\)]*(::[0-9]{10})*)$");
        if (re.matches(stops)) {
            List<BvgLocationType> types = BvgLocationType.fromSerializedString(stops);
            addedItemsList.addAll(types);
        } else {
            Toast.makeText(getApplicationContext(), "String has wrong format", Toast.LENGTH_SHORT).show();
            // overwrite faulty preference string
            SharedPreferencesManager.writeKey(getApplicationContext(), ConfigurationActivity.KEY_SAVED_BVG_STOPS, "");
            Log.e("PREFERENCES", "Stored string does not match the serialization convention");
            Log.e("PREFERENCES", stops);
        }

        ListView addedStationsListView = findViewById(R.id.lv_added_stations);

        ArrayAdapter<BvgLocationType> addedItemsAdapter = new BvgLocationTypeArrayAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_2, R.id.tv_location_name, addedItemsList);

        addedStationsListView.setAdapter(addedItemsAdapter);

        AutoCompleteTextView acTextViewStations = findViewById(R.id.ac_stations);

        acTextViewStations.setOnItemClickListener((adapterView, view, i, l) -> {
            addedItemsList.add(apiCompletedLocations.get(i));
            addedItemsAdapter.notifyDataSetChanged();
            acTextViewStations.setText("");
        });

        addedStationsListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            addedItemsList.remove(i);
            addedItemsAdapter.notifyDataSetChanged();
            return true;
        });

        ArrayAdapter<BvgLocationType> autocompleteAdapter = new BvgLocationTypeArrayAdapter(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, R.id.tv_location_name, apiCompletedLocations);
        acTextViewStations.setAdapter(autocompleteAdapter);

        final int[] lastExecutionId = {0};

        acTextViewStations.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    return;
                }
                bvgLocationsCommand = new ApiRequestCommandLocations.ApiRequestCommandLocationsBuilder(charSequence.toString())
                        .setLanguage("de")
                        .setLinesOfStop(true)
                        .setIncludeFuzzyMatches(true)
                        .setPretty(false)
                        .setResults(AUTOCOMPLETION_SUGGESTIONS)
                        .setShowAddresses(true)
                        .setShowPoi(true)
                        .setShowStops(true)
                        .build();

                lastExecutionId[0] += 1;

                bvgCommandExecutor = new ApiCommandExecutor(response -> {
                    if (bvgCommandExecutor.getCommandId() != lastExecutionId[0]) {
                        Log.d(LOG_KEY_BVG_API_RESPONSE, "Found expired command id");
                        return;
                    }
                    Gson gson = new Gson();
                    BvgLocation[] locations;
                    try {
                        locations = gson.fromJson(response, BvgLocation[].class);
                    } catch (JsonSyntaxException e) {
                        Log.d(LOG_KEY_BVG_API_RESPONSE, "JSON parse error");
                        return;
                    }
                    if (locations != null) {
                        apiCompletedLocations.clear();
                        for (BvgLocation location : locations) {
                            apiCompletedLocations.add(new BvgLocationType(location.name, location.products, location.id));
                        }
                        Log.d(LOG_KEY_BVG_API_RESPONSE, "Got " + apiCompletedLocations.size() + " locations");
                    } else
                        Log.e(LOG_KEY_BVG_API_RESPONSE, "Received no locations, error in response");

                    ContextCompat.getMainExecutor(getApplicationContext()).execute(() -> {
                        acTextViewStations.setCompletionHint(apiCompletedLocations.size() == 0 ?
                                getString(R.string.ac_api_location_hint_nothing) : getString(R.string.ac_api_location_hint_found));
                        acTextViewStations.showDropDown();
                        autocompleteAdapter.notifyDataSetChanged();
                    });
                }, lastExecutionId[0]);

                bvgCommandExecutor.execute(bvgLocationsCommand);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    // listener for finish button
    public void onFinishClick(View view) {

        if (addedItemsList.size() > 0)
            SharedPreferencesManager.writeKey(this, KEY_SAVED_BVG_STOPS,
                    addedItemsList.stream().map(BvgLocationType::serializeWithId).collect(Collectors.joining(";")));
        else
            return;

        // Set return intent type
        Intent resultValue = new Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

}