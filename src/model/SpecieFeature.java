package model;

import java.util.ArrayList;

public class SpecieFeature {
    private final String name;
    private final int geoHashPrecision;
    private int minOccurrence;
    private int maxOccurrence;
    private ArrayList<Feature> featureList;

    public SpecieFeature() {
        name = "";
        geoHashPrecision = 0;
        featureList = new ArrayList<>();
        minOccurrence = 0;
        maxOccurrence = 0;
    }

    public SpecieFeature(String name, ArrayList<Feature> featureList, int geoHashPrecision) {
        this.geoHashPrecision = geoHashPrecision;
        this.name = name;
        this.featureList = featureList;

        if (!generateGeoHash()) {
            System.out.println("[SpecieFeature]: Could not generate geoHash.");
        } else {
            generateMinMax();
        }
    }

    public int getGeoHashPrecision() {
        return geoHashPrecision;
    }

    public static boolean isGeoHashValid(int geoHashPrecision) {
        return geoHashPrecision > 0 && geoHashPrecision < 12;
    }

    private boolean generateGeoHash() {
        if (isGeoHashValid(geoHashPrecision)) {
            for (Feature f : featureList)
                f.generateGeoHash(geoHashPrecision);
            return true;
        } else
            return false;
    }

    private void generateMinMax() {
        ArrayList<Integer> occurrences = getOccurrencesArrays();

        if (occurrences.size() > 0) {
            int min = occurrences.get(0);
            int max = occurrences.get(0);
            for (int i : occurrences) {
                min = Integer.min(i, min);
                max = Integer.max(i, max);
            }
            minOccurrence = min;
            maxOccurrence = max;
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Feature> getFeatureList() {
        return featureList;
    }

    public int getOccurrences(String geoHash) {
        try {
            int i = 0;
            for (Feature feature : featureList)
                if (GeoHash.convertCoordinatesToGeoHash(feature.getCoordinates(), geoHashPrecision).equals(geoHash))
                    i++;
            return i;
        } catch (Exception e) {
            System.out.println("Espèce non reconnue");
            return 0;
        }
    }

    private ArrayList<Integer> getOccurrencesArrays() {
        ArrayList<String> zones = new ArrayList<>();
        ArrayList<Integer> occurrences = new ArrayList<>();

        for (Feature feature : featureList) {
            if (zones.size() == 0) {
                zones.add(feature.getGeoHash());
                occurrences.add(1);
            } else {
                boolean add = false;
                for (int i = 0; i < zones.size(); i++) {
                    if (zones.get(i).equals(feature.getGeoHash()))
                        occurrences.set(i, occurrences.get(i) + 1);
                    else {
                        add = true;
                    }
                }
                if (add) {
                    zones.add(feature.getGeoHash());
                    occurrences.add(1);
                }
            }
        }
        return occurrences;
    }

    public int getMinOccurrences() {
        return minOccurrence;
    }

    public int getMaxOccurrences() {
        return maxOccurrence;
    }

    public float getZoneDensity(String geoHash) {
        float min = (float) minOccurrence;
        float max = (float) maxOccurrence;
        if (min == max)
            return 0;
        else
            return (getOccurrences(geoHash) - min) / (max - min);
    }
}
