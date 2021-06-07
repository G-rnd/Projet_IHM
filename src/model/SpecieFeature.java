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

    public String getName() {
        return name;
    }

    public int getNbFeatures() {
        return featureList.size();
    }

    public int getNbIndividuals() {
        int i = 0;
        for(Feature f : featureList) {
            i += f.getN();

        }
        return i;
    }

    public int getGeoHashPrecision() {
        return geoHashPrecision;
    }

    public int getMinOccurrence() {
        return minOccurrence;
    }

    public int getMaxOccurrence() {
        return maxOccurrence;
    }

    public ArrayList<Feature> getFeatureList() {
        return featureList;
    }

    public int getOccurrences(String geoHash) {
        try {
            int i = 0;
            for (Feature feature : featureList)
                if (GeoHash.convertCoordinatesToGeoHash(feature.getCoordinates(), geoHashPrecision).equals(geoHash))
                    i+= feature.getN();
            return i;
        } catch (Exception e) {
            System.out.println("Espèce non reconnue");
            return 0;
        }
    }

    public float getZoneDensity(String geoHash) {
        float min = (float) minOccurrence;
        float max = (float) maxOccurrence;
        if (min == max)
            return 0;
        else
            return (getOccurrences(geoHash) - min) / (max - min);
    }

    public ArrayList<ArrayList<Float>> getAllCoordinates() {
        ArrayList<ArrayList<Float>> zones = new ArrayList<>();
        for (Feature feature : featureList) {
            if (zones.size() == 0) {
                zones.add(feature.getCoordinates());
            } else {
                boolean add = true;
                for (ArrayList<Float> zone : zones) {
                    boolean equals = true;
                    for(int i = 0; i < zone.size(); i++)
                        if (!zone.get(i).equals(feature.getCoordinates().get(i))) {
                            equals = false;
                            break;
                        }
                    if (equals) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    zones.add(feature.getCoordinates());
                }
            }
        }
        return zones;
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
        ArrayList<Integer> occurrences = getOccurrencesArray();
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

    private ArrayList<Integer> getOccurrencesArray() {
        ArrayList<String> zones = new ArrayList<>();
        ArrayList<Integer> occurrences = new ArrayList<>();

        for (Feature feature : featureList) {
            if (zones.size() == 0) {
                zones.add(feature.getGeoHash());
                occurrences.add(feature.getN());
            } else {
                boolean add = false;
                for (int i = 0; i < zones.size(); i++) {
                    if (zones.get(i).equals(feature.getGeoHash()))
                        occurrences.set(i, occurrences.get(i) + feature.getN());
                    else {
                        add = true;
                    }
                }
                if (add) {
                    zones.add(feature.getGeoHash());
                    occurrences.add(feature.getN());
                }
            }
        }
        return occurrences;
    }

    public static boolean isGeoHashValid(int geoHashPrecision) {
        return geoHashPrecision > 0 && geoHashPrecision < 12;
    }
}
