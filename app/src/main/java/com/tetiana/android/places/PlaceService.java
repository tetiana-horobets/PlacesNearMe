package com.tetiana.android.places;

import java.util.ArrayList;
import java.util.List;

class PlaceService {

    List<String> findPlaces(String searchCriteria) {
        ArrayList<String> results = new ArrayList<>();

        if (!searchCriteria.equals("void")) {
            results.add("Biedronka");
            results.add("Zabka");
            results.add("Auchan");
            results.add("Lidl");
        }

        return results;
    }
}
