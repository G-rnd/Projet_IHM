package model;

import java.util.ArrayList;

public class Feature {
    private final int n;
    private final ArrayList<Float> coordinates;

    public Feature() {
        n = 0;
        coordinates = new ArrayList<>();
    }

    public Feature(int n, ArrayList<Float> coordinates) {
        this.n = n;
        this.coordinates = coordinates;
    }

    public int getN() {
        return n;
    }

    public ArrayList<Float> getZone() {
        return coordinates;
    }
}