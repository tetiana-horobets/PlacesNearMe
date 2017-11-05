package com.tetiana.android.places.service;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class PlaceSearchResult {

    @Element private String id;
    @Element private String name;
    @Element private String vicinity;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVicinity() {
        return vicinity;
    }
}
