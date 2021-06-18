package model;

import java.util.ArrayList;

public class Feature {
    /**
     * Nombre d'individus de l'observation.
     */
    private final int n;
    /**
     * Coordonnées de l'observation sous la forme : [lat1, long1, lat2, long2].
     */
    private final ArrayList<Float> coordinates;
    /**
     * GeoHash de l'observation.
     */
    private String geoHash;

    public Feature() {
        n = 0;
        geoHash = "";
        coordinates = new ArrayList<>();
    }

    public Feature(ArrayList<Float> coordinates, int n) {
        this.n = n;
        this.coordinates = coordinates;
    }

    /**
     * Retourne le geoHash de l'observation.
     *
     * @return Le membre geoHash.
     */
    public String getGeoHash() {
        return geoHash;
    }

    /**
     * Retourne le nombre d'individus de l'observation.
     *
     * @return Le membre n.
     */
    public int getN() {
        return n;
    }

    /**
     * Retourne les coordonnées du geoHash.
     *
     * @return Le membre coordinates.
     */
    public ArrayList<Float> getCoordinates() {
        return coordinates;
    }

    /**
     * Génère le geoHash de l'observation.
     *
     * @param precision Le degré de précision du geoHash.
     */
    public void generateGeoHash(int precision) {
        if (precision > 0 && precision < 12)
            geoHash = GeoHash.convertCoordinatesToGeoHash(coordinates, precision);
    }
}