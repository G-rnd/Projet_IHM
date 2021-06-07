package model;

import data.FileReader;
import data.ObisReader;

import java.util.ArrayList;

public class Model {
    private SpecieFeature specie;
    private ArrayList<ObservationDetails> observationDetails;
    private ArrayList<String> names;

    public Model(String filename) {
        observationDetails = new ArrayList<>();
        names = new ArrayList<>();

        if (!loadLocalFile(filename))
            System.out.println("[Model]: Could not load" + filename);
    }

    public ArrayList<ArrayList<Float>> getAllCoordinates() {
        return specie.getAllCoordinates();
    }

    public int getOccurrence(String geoHash) {
        return specie.getOccurrences(geoHash);
    }

    public float getDensity(String geoHash) {
        return specie.getZoneDensity(geoHash);
    }

    public int getMinOccurrence() {
        return specie.getMinOccurrence();
    }

    public int getMaxOccurrence() {
        return specie.getMaxOccurrence();
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<ObservationDetails> getObservationDetails() {
        return observationDetails;
    }

    public String getName() {
        return specie.getName();
    }

    public int getNbFeatures() {
        return specie.getNbFeatures();
    }

    public int getNbIndividuals() {
        return specie.getNbIndividuals();
    }

    public int getGeoHashPrecision() {
        return specie.getGeoHashPrecision();
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
