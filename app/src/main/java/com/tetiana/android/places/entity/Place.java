package com.tetiana.android.places.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Place {

    @Element
    private String id;
    @Element
    private String name;
    @Element
    private String vicinity;
    @Element
    private Geometry geometry;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public double getLat() {
        return geometry.location.lat;
    }

    public double getLng() {
        return geometry.location.lng;
    }

    @Root(strict = false)
    public static class Geometry {

        @Element
        private Location location;

        @Root(strict = false)
        public static class Location {

            @Element
            private double lat;
            @Element
            private double lng;
        }
    }
}
