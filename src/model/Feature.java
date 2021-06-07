package model;

import java.util.ArrayList;

public class Feature {
    private final int n;
    private String geoHash;
    private final ArrayList<Float> coordinates;


    public Feature() {
        n = 0;
        geoHash = "";
        coordinates = new ArrayList<>();
    }

    public Feature(ArrayList<Float> coordinates, int n) {
        this.n = n;
        this.coordinates = coordinates;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public int getN() {
        return n;
    }
    public ArrayList<Float> getCoordinates() {
        return coordinates;
    }

    public boolean generateGeoHash(int precision) {
        if (precision > 0 && precision < 12) {
            geoHash = GeoHash.convertCoordinatesToGeoHash(coordinates, precision);
            return true;
        }
        return false;
    }
}