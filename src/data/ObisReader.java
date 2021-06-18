package data;

import model.ObservationDetails;
import model.SpecieFeature;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ObisReader {
    /**
     * Format des dates.
     */
    public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Récupère le nombre de signalement par région pour un nom d’espèce passé en paramètre.
     *
     * @param name             Le nom de l'espèce.
     * @param geoHashPrecision La précision du GeoHash.
     * @return Les données lues sur Obis.
     */
    public static SpecieFeature loadOccurrencesFile(String name, int geoHashPrecision) {
        return ObisParser.parseOccurrencesJsonObject(FileReader.readJsonObjectFromUrl(
                "https://api.obis.org/v3/occurrence/grid/" + geoHashPrecision + "?scientificname=" + name
        ), name, geoHashPrecision);
    }

    /**
     * Récupère le nombre de signalement par région pour un nom d’espèce et entre deux dates passé en
     * paramètre.
     *
     * @param name             Le nom de l'espèce
     * @param geoHashPrecision La précision du GeoHash
     * @param startDate        La date de début
     * @param endDate          La date de fin
     * @return Les données lues sur Obis.
     */
    public static SpecieFeature loadOccurrencesFile(String name, int geoHashPrecision, String startDate, String endDate) {
        try {
            format.parse(startDate);
            format.parse(endDate);

            return ObisParser.parseOccurrencesJsonObject(FileReader.readJsonObjectFromUrl(
                    "https://api.obis.org/v3/occurrence/grid/" + geoHashPrecision + "?scientificname=" + name
                            + "&startdate=" + startDate + "&enddate=" + endDate
            ), name, geoHashPrecision);
        } catch (ParseException e) {
            System.out.println("Error Date Format");
            return null;
        }
    }

    /**
     * Récupère les fichiers lus suite à une requête Obis, en passant en paramètre le de l'espèce, la précision
     * du geoHash, la date de début, le temps entre deux intervalles et le nombre d'intervalles.
     *
     * @param name             Le nom de l'espèce.
     * @param geoHashPrecision Le degré de précision du geoHash.
     * @param startDate        La date de début.
     * @param timeInterval     Le temps entre deux intervalles.
     * @param nbIntervals      Le nombre d'intervalles.
     * @return La liste des Observations faites sur chaque intervalle.
     */
    public static ArrayList<SpecieFeature> loadOccurrencesFiles(String name, int geoHashPrecision, String startDate,
                                                                int timeInterval, int nbIntervals) {
        try {
            format.parse(startDate);
        } catch (Exception e) {
            System.out.println("Error Date format");
            return null;
        }

        if (nbIntervals > 0 && timeInterval >= 0) {
            ArrayList<SpecieFeature> ret = new ArrayList<>();

            Date start;
            try {
                start = format.parse(startDate);
            } catch (ParseException e) {
                System.out.println("Error Date Format");
                return null;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(start);

            for (int i = 0; i < nbIntervals; i++) {
                c.add(Calendar.DATE, timeInterval);
                String endDate = format.format(c.getTime());
                ret.add(loadOccurrencesFile(name, geoHashPrecision, startDate, endDate));
                startDate = endDate;
            }
            return ret;
        } else return null;
    }

    /**
     * Récupère les premiers noms des espèces commençant par une chaîne de caractère
     * passée en paramètre
     *
     * @param initialLetters La chaîne de caractère.
     * @return Une liste de noms d'espèces.
     */
    public static ArrayList<String> loadNamesFromLetters(String initialLetters) {
        return ObisParser.parseScientificNameJsonArray(
                FileReader.readJsonArrayFromUrl("https://api.obis.org/v3/taxon/complete/verbose/" + initialLetters)
        );
    }

    /**
     * Récupère les premiers noms des espèces présentes pour un GeoHash
     *
     * @param geoHash La zone considérée.
     * @return Une liste de noms d'espèces.
     */
    public static ArrayList<String> loadNamesFromGeoHash(String geoHash) {
        return ObisParser.parseScientificNameJsonObject(FileReader.readJsonObjectFromUrl(
                "https://api.obis.org/v3/occurrence?geometry=" + geoHash
        ));
    }

    /**
     * Récupérer les détails d’enregistrement pour un GeoHash
     *
     * @param geoHash La zone considérée.
     * @return La liste des détails d'enregistrement.
     */
    public static ArrayList<ObservationDetails> loadObservationDetails(String geoHash) {
        return ObisParser.parseObservationDetailsJsonObject(
                FileReader.readJsonObjectFromUrl(
                        "https://api.obis.org/v3/occurrence?geometry=" + geoHash
                )
        );
    }

    /**
     * Récupérer les détails d’enregistrement pour un nom d’espèce passé en paramètre et un GeoHash
     *
     * @param geoHash La zone considérée.
     * @param name    Le nom de l'espèce
     * @return La liste des détails d'enregistrement.
     */
    public static ArrayList<ObservationDetails> loadObservationDetails(String geoHash, String name) {
        return ObisParser.parseObservationDetailsJsonObject(
                FileReader.readJsonObjectFromUrl(
                        "https://api.obis.org/v3/occurrence?scientificname="+name+"&amp;geometry=" + geoHash
                )
        );
    }
}
