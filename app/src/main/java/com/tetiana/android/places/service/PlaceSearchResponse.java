package com.tetiana.android.places.service;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class PlaceSearchResponse {

    @ElementList(entry="result", inline = true)
    private List<PlaceSearchResult> result;

    public List<PlaceSearchResult> getResult() {
        return result;
    }
}
