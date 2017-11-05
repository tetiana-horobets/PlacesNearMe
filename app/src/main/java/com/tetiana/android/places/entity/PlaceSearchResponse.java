package com.tetiana.android.places.entity;

import com.tetiana.android.places.entity.Place;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class PlaceSearchResponse {

    @ElementList(entry="result", inline = true)
    private List<Place> result;

    public List<Place> getResult() {
        return result;
    }
}
