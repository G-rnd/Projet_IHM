package model;

import java.util.ArrayList;

public class SpecieFeature {
    private final int id;
    private ArrayList<Feature> featureList;

    public SpecieFeature() {
        id = 0;
        featureList = new ArrayList<>();
    }

    public SpecieFeature(int id, ArrayList<Feature> featureList) {
        this.id = id;
        this.featureList = featureList;
    }

    public int getOccurrences(String GeoHash) {
        return 0;
    }

    public int getMinOccurrences() {
        return 0;
    }

    public int getMaxOccurrences() {
        return 0;
    }

    public ArrayList<Float> getMinOccurrenceZone() {
        return new ArrayList<>();
    }

    public ArrayList<Float> getMaxOccurrenceZone() {
        return new ArrayList<>();
    }

    public float getZoneDensity(String geoHash) {
        return 0.0f;
    }
}
