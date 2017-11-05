package com.tetiana.android.places.service;

import com.tetiana.android.places.entity.Place;

import java.util.List;

public interface PlacesSearchCallback {
    void foundPlaces(List<Place> places);
}
