package com.tetiana.android.places;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_RESULTS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PlaceService placeService = new PlaceService();
        final EditText enterText = (EditText) findViewById(R.id.search_field);
        final Button showMap = (Button) findViewById(R.id.show_map);
        final ListView listView = (ListView) findViewById(R.id.found_places);
        final ArrayList<String> placesList = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                placesList);

        listView.setAdapter(arrayAdapter);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchCriteria = enterText.getText().toString();
                List<String> places = placeService.findPlaces(searchCriteria);

                placesList.clear();
                for (int i = 0; i < Math.min(MAX_RESULTS, places.size()); i++) {
                    placesList.add(places.get(i));
                }

                arrayAdapter.notifyDataSetChanged();

                if (placesList.isEmpty()) {
                    showMap.setVisibility(View.GONE);
                } else {
                    showMap.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
