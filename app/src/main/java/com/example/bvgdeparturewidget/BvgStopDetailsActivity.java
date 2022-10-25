package com.example.bvgdeparturewidget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bvgdeparturewidget.bvgrestapi.ApiCommandExecutor;
import com.example.bvgdeparturewidget.bvgrestapi.ApiRequestCommandStopDepartures;
import com.example.bvgdeparturewidget.bvgrestapi.bvgjsonresponses.BvgDeparture;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BvgStopDetailsActivity extends AppCompatActivity {

    private final List<BvgDeparture> departures = new ArrayList<>();
    private BvgDeparturesArrayAdapter departuresArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bvg_departue_details);

        switchStage(ACTIVITY_STAGE.LOADING);

        Intent intent = getIntent();
        String stationName = intent.getStringExtra(BvgWidget.EXTRA_LIST_ON_CLICK_STATION_NAME);
        String stationId = intent.getStringExtra(BvgWidget.EXTRA_LIST_ON_CLICK_STATION_ID);

        setTitle("Departures: " + stationName);

        ApiRequestCommandStopDepartures command = new ApiRequestCommandStopDepartures.ApiRequestCommandStopDeparturesBuilder(stationId)
                .setLanguage("de")
                .setDuration(15)
                .setPretty(false)
                .build();

        ListView listViewFilteredDepartures = findViewById(R.id.list_view_filtered_departures);
        departuresArrayAdapter = new BvgDeparturesArrayAdapter(this, departures);
        listViewFilteredDepartures.setAdapter(departuresArrayAdapter);

        ApiCommandExecutor executor = new ApiCommandExecutor(response -> {
            try {
                runOnUiThread(() -> findViewById(R.id.loadingPanel).setVisibility(View.GONE));
                BvgDeparture[] departuresArray = command.parseResponse(response);

                departures.clear();
                if (departuresArray != null) {
                    departures.addAll(Arrays.asList(departuresArray));
                    switchStage(ACTIVITY_STAGE.SHOW_RESULTS);
                } else {
                    switchStage(ACTIVITY_STAGE.ERROR);
                    ((TextView) findViewById(R.id.text_view_error_message)).setText(command.getResponse());
                }
                runOnUiThread(departuresArrayAdapter::notifyDataSetChanged);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        });

        executor.execute(command);

        ChipGroup chipGroup = findViewById(R.id.chip_group_selectedServices);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.size() == 0)
                return;
            StringBuilder sb = new StringBuilder();
            for (int id : checkedIds) {
                sb.append((String) group.findViewById(id).getTag());
                sb.append(',');
            }
            departuresArrayAdapter.getFilter().filter(sb.substring(0, sb.length() - 1));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if(chipGroup != null){
            try {
                String[] checkedBoxes;
                checkedBoxes = SharedPreferencesManager.getStringKey(this, SP_KEY_CHECKED_CHIPS).split(",");
                for(String s_id : checkedBoxes){
                    if(s_id.length() > 0) {
                        int i = Integer.parseInt(s_id);
                        Log.d("CHECKBOX", "Checked " + i);
                        chipGroup.check(i);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Serialize checked chips
/*        if(chipGroup != null){
            chipGroup = findViewById(R.id.chip_group_selectedServices);
            StringBuilder sb = new StringBuilder("");
            for(Integer id : chipGroup.getCheckedChipIds()){

                Log.d("CHECKBOX", "Save id " + id);
                sb.append(id);
                sb.append(',');
            }
            try {
                SharedPreferencesManager.writeKey(this, SP_KEY_CHECKED_CHIPS,
                        sb.length() == 0 ? "" : sb.substring(0, sb.length()-1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private void switchStage(ACTIVITY_STAGE toStage) {
        switch (toStage) {
            case SHOW_RESULTS:
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                findViewById(R.id.list_view_filtered_departures).setVisibility(View.VISIBLE);
                findViewById(R.id.text_view_error_message).setVisibility(View.GONE);
                break;
            case LOADING:
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                findViewById(R.id.list_view_filtered_departures).setVisibility(View.GONE);
                findViewById(R.id.text_view_error_message).setVisibility(View.GONE);
                break;
            case ERROR:
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                findViewById(R.id.list_view_filtered_departures).setVisibility(View.GONE);
                findViewById(R.id.text_view_error_message).setVisibility(View.VISIBLE);
                break;
        }
    }

    private enum ACTIVITY_STAGE {
        SHOW_RESULTS,
        LOADING,
        ERROR
    }
}
