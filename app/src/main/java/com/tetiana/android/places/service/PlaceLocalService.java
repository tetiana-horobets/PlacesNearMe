package com.tetiana.android.places.service;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.tetiana.android.places.entity.PlaceSearchResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class PlaceLocalService {

    public static final String SEARCH_RADIUS = "10000";

    private final Activity mActivity;
    private final String mApiKey;
    private final PlaceRemoteService mPlaceRemoteService;
    private final LocationService mLocationService;

    public PlaceLocalService(Activity activity) {
        mActivity = activity;

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .build();

        mPlaceRemoteService = retrofit.create(PlaceRemoteService.class);
        mLocationService = new LocationService(activity);
        mApiKey = getMetadata().getString("com.google.android.geo.API_KEY");
    }

    public void findPlaces(String name, final PlacesSearchCallback resultCallback) {
        LatLng location = mLocationService.getLocation();
        Map<String, String> query = new HashMap<>();

        query.put("key", mApiKey);
        query.put("name", name);
        query.put("radius", SEARCH_RADIUS);
        query.put("types", "establishment");
        query.put("location", location.latitude + "," + location.longitude);

        Call<PlaceSearchResponse> call = mPlaceRemoteService.findPlaces(query);

        call.enqueue(new Callback<PlaceSearchResponse>() {
            @Override
            public void onResponse(Call<PlaceSearchResponse> call, Response<PlaceSearchResponse> response) {
                resultCallback.foundPlaces(response.body().getResult());
            }

            @Override
            public void onFailure(Call<PlaceSearchResponse> call, Throwable t) {
                Log.e("PlaceService", "onFailure: ", t);
            }
        });
    }

    private Bundle getMetadata() {
        try {
            ApplicationInfo applicationInfo = mActivity.getPackageManager()
                    .getApplicationInfo(mActivity.getPackageName(), PackageManager.GET_META_DATA);

            return applicationInfo.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Main activity", "Unable to get metadata: ", e);
            return null;
        }
    }
}
