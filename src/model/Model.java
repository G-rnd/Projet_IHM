package model;

import java.util.ArrayList;
import java.util.Date;

public class Model {
    private ArrayList<SpecieFeature> specieList;

    public Model() {
        specieList = new ArrayList<>();
    }

    public SpecieFeature getSpecie(int id) {
        return new SpecieFeature();
    }

    public int getSpeciesOccurrence(String name, String geoHash) {
        return 0;
    }

    public int getSpeciesOccurrence(String name, String geoHash, int geoHashPrecision, int queryInterval) {
        return 0;
    }

    public void loadRecords(String name, int geoHashPrecision, Date startDate, int timeInterval, int nbIntervals) {
    }

    public static String convertGPStoGeoHash(float latitude, float longitude, int geoHashPrecision) {
        return "";
    }

    public ArrayList<ObservationDetails> getObservationDetails(String geoHash) {
        return new ArrayList<>();
    }

    public ArrayList<ObservationDetails> getObservationDetails(String geoHash, String name) {
        return new ArrayList<>();
    }

    public ArrayList<String> getSpeciesNames(String initialLetters) {
        return new ArrayList<>();
    }
}
