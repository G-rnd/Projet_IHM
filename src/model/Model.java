package model;

import data.FileReader;
import data.ObisReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Model {
    public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SpecieFeature specie;
    private ArrayList<ObservationDetails> observationDetails;
    private ArrayList<String> names;

    public Model(String filename) {
        format.setLenient(false);
        observationDetails = new ArrayList<>();
        names = new ArrayList<>();

        if (!loadLocalFile(filename))
            System.out.println("[Model]: Could not load" + filename);
    }

    public ArrayList<ArrayList<Float>> getAllCoordinates() {
        ArrayList<ArrayList<Float>> ret = new ArrayList<>();
        for (Feature feature : specie.getFeatureList())
            ret.add(feature.getCoordinates());
        return ret;
    }

    public int getOccurrence(String geoHash) {
        return specie.getOccurrences(geoHash);
    }

    public float getDensity(String geoHash) {
        return specie.getZoneDensity(geoHash);
    }

    public int getMinOccurrence() {
        return specie.getMinOccurrences();
    }

    public int getMaxOccurrence() {
        return specie.getMaxOccurrences();
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public SpecieFeature getSpecie() {
        return specie;
    }

    public ArrayList<ObservationDetails> getObservationDetails() {
        return observationDetails;
    }

    public boolean loadLocalFile(String name) {
        specie = FileReader.readLocalFile(name);
        return specie != null;
    }

    public boolean loadObisFile(String name, int geoHashPrecision) {
        if (SpecieFeature.isGeoHashValid(geoHashPrecision)) {
            specie = ObisReader.loadOccurrencesFile(name, geoHashPrecision);
            return true;
        } else return false;
    }

    public boolean loadObisFile(String name, int geoHashPrecision, String startDate, String endDate) {
        if (SpecieFeature.isGeoHashValid(geoHashPrecision)) {
            specie = ObisReader.loadOccurrencesFile(name, geoHashPrecision, startDate, endDate);
            return specie != null;
        } else return false;
    }

    public boolean loadNamesFromLetters(String initialLetters) {
        names = ObisReader.loadNamesFromLetters(initialLetters);
        return names.size() != 0;
    }

    public boolean loadNamesFromGeoHash(String geoHash) {
        names = ObisReader.loadNamesFromGeoHash(geoHash);
        return names.size() != 0;
    }

    public boolean loadObservationDetails(String geoHash) {
        observationDetails = ObisReader.loadObservationDetails(geoHash);
        return observationDetails.size() != 0;
    }

    public boolean loadObservationDetails(String geoHash, String name) {
        observationDetails = ObisReader.loadObservationDetails(geoHash, name);
        return observationDetails.size() != 0;
    }


}
