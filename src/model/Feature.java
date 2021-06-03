package model;

import java.util.ArrayList;
import java.util.Date;

public class Feature {
    private final int n;
    private final ArrayList<Float> coordinates;
    private final Date date;

    public Feature() {
        n = 0;
        coordinates = new ArrayList<>();
        date = new Date();
    }

    public Feature(int n, ArrayList<Float> coordinates, Date date) {
        this.n = n;
        this.coordinates = coordinates;
        this.date = date;
    }

    public int getN() {
        return n;
    }

    public ArrayList<Float> getZone() {
        return coordinates;
    }

    private Date getDate() {
        return date;
    }
}