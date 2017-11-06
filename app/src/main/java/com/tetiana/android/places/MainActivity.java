package com.tetiana.android.places;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tetiana.android.places.service.LocationService;
import com.tetiana.android.places.service.PlaceLocalService;
import com.tetiana.android.places.entity.Place;
import com.tetiana.android.places.service.PlacesSearchCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_RESULTS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LocationService locationService = new LocationService(this);
        final PlaceLocalService placeService = new PlaceLocalService(this);
        final EditText enterText = (EditText) findViewById(R.id.search_field);
        final Button showMap = (Button) findViewById(R.id.show_map);
        final Button showList = (Button) findViewById(R.id.show_list);
        final ListView listView = (ListView) findViewById(R.id.found_places);
        final ArrayList<String> placesList = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                placesList);

        listView.setAdapter(arrayAdapter);

        final SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.getView().setVisibility(View.INVISIBLE);

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                Button searchButton = (Button) findViewById(R.id.search_button);
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        map.getView().setVisibility(View.GONE);
                        showList.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        showMap.setVisibility(View.VISIBLE);

                        LatLng location = locationService.getLocation();
                        MarkerOptions userMarker = new MarkerOptions().position(location).title("You");
                        userMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                        googleMap.addMarker(userMarker);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 11));

                        String searchCriteria = enterText.getText().toString();
                        placeService.findPlaces(searchCriteria, new PlacesSearchCallback() {
                            @Override
                            public void foundPlaces(List<Place> places) {
                                placesList.clear();
                                for (int i = 0; i < Math.min(MAX_RESULTS, places.size()); i++) {
                                    Place place = places.get(i);
                                    placesList.add(place.getName() + "\n" + place.getVicinity());

                                    LatLng latLng = new LatLng(place.getLat(), place.getLng());
                                    MarkerOptions marker = new MarkerOptions().position(latLng).title(place.getName());
                                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                                    googleMap.addMarker(marker);
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
                });
            }
        });

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                showMap.setVisibility(View.GONE);
                map.getView().setVisibility(View.VISIBLE);
                showList.setVisibility(View.VISIBLE);
            }
        });

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.getView().setVisibility(View.GONE);
                showList.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                showMap.setVisibility(View.VISIBLE);
            }
        });
    }
}
