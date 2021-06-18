package model;

import java.util.ArrayList;

public class GeoHash {
    /**
     * Caractères de la Base32.
     */
    public static final String base32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    /**
     * Génère un geoHash à partir des coordonnées d'une observation et la précision du geoHash.
     *
     * @param coords           Les coordonnées d'une observation.
     * @param geoHashPrecision La précision du geoHash.
     * @return Le geoHash généré.
     */
    public static String convertCoordinatesToGeoHash(ArrayList<Float> coords, int geoHashPrecision) {
        if (coords.size() == 4)
            return convertGPStoGeoHash(
                (coords.get(1) + coords.get(3)) / 2,(coords.get(0) + coords.get(2)) / 2, geoHashPrecision
            );
        else return "";
    }

    /**
     * Génère un geoHash à partir d'un point GPS et de la précision du geoHash.
     *
     * @param latitude         La latitude du point.
     * @param longitude        La longitude du point.
     * @param geoHashPrecision La précision du geoHash.
     * @return Le geoHash généré.
     */
    public static String convertGPStoGeoHash(float latitude, float longitude, int geoHashPrecision) {
        StringBuilder geoHash = new StringBuilder();
        int length = 0;
        int geoInt = 0;

        boolean toggle = false;
        int compt = 0;

        float longMin = -180;
        float longMax = 180;

        float latMin = -90;
        float latMax = 90;

        while (length < geoHashPrecision) {
            float longMil = (longMax + longMin) / 2;
            float latMil = (latMax + latMin) / 2;

            if (toggle) {
                if (latitude > latMil) {
                    geoInt += 1;
                    latMin = latMil;
                } else {
                    latMax = latMil;
                }
            } else {
                if (longitude > longMil) {
                    geoInt += 1;
                    longMin = longMil;
                } else {
                    longMax = longMil;
                }
            }
            toggle = !toggle;
            compt++;

            if (compt == 5) {
                geoHash.append(base32.charAt(geoInt));
                length++;
                geoInt = 0;
                compt = 0;
            } else {
                geoInt <<= 1;
            }
        }
        return geoHash.toString();
    }
}
