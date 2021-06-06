package model;

import java.util.ArrayList;

public class GeoHash {
    public static final String base32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    public static String convertCoordinatesToGeoHash(ArrayList<Float> coords, int geoHashPrecision) {
        return convertGPStoGeoHash(
                (coords.get(0) + coords.get(4)) / 2, (coords.get(1) + coords.get(5)) / 2, geoHashPrecision
        );
    }

    public static String convertGPStoGeoHash(float latitude, float longitude, int geoHashPrecision) {
        StringBuilder geoHash = new StringBuilder("");
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

    public static String convertGPStoGeoHash(float latitude, float longitude) {
        return convertGPStoGeoHash(latitude, longitude, 3);
    }
}
