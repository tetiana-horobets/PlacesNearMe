package com.tetiana.android.places.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

interface PlaceRemoteService {

    @GET("place/nearbysearch/xml")
    Call<PlaceSearchResponse> findPlaces(@QueryMap Map<String, String> parameters);
}
