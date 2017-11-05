package com.tetiana.android.places.service;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class PlaceLocalService {

    public static final String SEARCH_RADIUS = "10000";

    private final PlaceRemoteService placeRemoteService;
    private final String apiKey;

    public PlaceLocalService(Bundle metadata) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .build();

        placeRemoteService = retrofit.create(PlaceRemoteService.class);
        apiKey = metadata.getString("com.google.android.geo.API_KEY");
    }

    public void findPlaces(String name, final PlacesSearchCallback resultCallback) {
        Map<String, String> query = new HashMap<>();

        query.put("key", apiKey);
        query.put("name", name);
        query.put("radius", SEARCH_RADIUS);
        query.put("types", "establishment");
        query.put("location", "54.4066879,18.617713");

        Call<PlaceSearchResponse> call = placeRemoteService.findPlaces(query);

        call.enqueue(new Callback<PlaceSearchResponse>() {
            @Override
            public void onResponse(Call<PlaceSearchResponse> call, Response<PlaceSearchResponse> response) {
                List<String> places = new ArrayList<>();

                for (PlaceSearchResult placeSearchResult : response.body().getResult()) {
                    places.add(placeSearchResult.getName());
                }

                resultCallback.foundPlaces(places);
            }

            @Override
            public void onFailure(Call<PlaceSearchResponse> call, Throwable t) {
                Log.e("PlaceService", "onFailure: ", t);
            }
        });
    }
}
