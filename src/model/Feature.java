package model;

import java.util.ArrayList;

public class Feature {
    private String geoHash;
    private final ArrayList<Float> coordinates;

    public Feature() {
        geoHash = "";
        coordinates = new ArrayList<>();
    }

    public Feature(ArrayList<Float> coordinates) {
        this.coordinates = coordinates;
    }

    public boolean generateGeoHash(int precision) {
        if (precision > 0 && precision < 12) {
            geoHash = GeoHash.convertCoordinatesToGeoHash(coordinates, precision);
            return true;
        }
        return false;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public ArrayList<Float> getCoordinates() {
        return coordinates;
    }
}