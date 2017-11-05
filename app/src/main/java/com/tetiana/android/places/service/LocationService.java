package com.tetiana.android.places.service;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class LocationService {

    public static final LatLng DEFAULT_LOCATION = new LatLng(54.3607613, 18.4091578);

    private final Activity mActivity;

    public LocationService(Activity activity) {
        mActivity = activity;
    }

    public LatLng getLocation() {
        checkPermissions();

        LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null) {
            return new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            return DEFAULT_LOCATION;
        }
    }

    private void checkPermissions() {
        if (checkSelfPermission(mActivity, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
                && checkSelfPermission(mActivity, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {

            String[] permissions = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
            requestPermissions(mActivity, permissions, 0);
        }
    }
}
