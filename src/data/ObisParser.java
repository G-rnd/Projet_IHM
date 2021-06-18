package data;

import model.Feature;
import model.ObservationDetails;
import model.SpecieFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ObisParser {

    /**
     * Récupère le nombre de signalements par région pour un nom d'espèce et une précision passées en paramètre.
     * @param jsonObject L'objet JSON à parser.
     * @param name Le nom de l'espèce.
     * @param geoHashPrecision Le degré de précision
     * @return Une SpecieFeature avec les informations lues.
     */
    public static SpecieFeature parseOccurrencesJsonObject(JSONObject jsonObject, String name, int geoHashPrecision) {
        try {
            JSONArray jsonRoot = jsonObject.getJSONArray("features");
            ArrayList<Feature> features = new ArrayList<>();
            for (int i = 0; i < jsonRoot.length(); i++) {
                features.add(new Feature(parseFloatArray(jsonRoot.getJSONObject(i).getJSONObject("geometry")
                        .getJSONArray("coordinates").getJSONArray(0)),
                        jsonRoot.getJSONObject(i).getJSONObject("properties").getInt("n"))
                );
            }
            return new SpecieFeature(name, features, geoHashPrecision);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Renvoie un tableau de flottants à partir d'un JSONArray.
     * @param jsonArray Le tableau JSON contenant les données.
     * @return Les coordonnées dans un seul tableau.
     */
    public static ArrayList<Float> parseFloatArray(JSONArray jsonArray) {
        ArrayList<Float> list = new ArrayList<>();
        for (int i = 0; i < 4; i+=2)
            for (int j = 0; j < 2; j++)
                try {
                    list.add(Float.parseFloat(jsonArray.getJSONArray(i).get(j).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        return list;
    }

    /**
     * Renvoie un tableau comportant les noms d'espèces se trouvant dans un tableau JSON.
     * @param jsonRoot Le tableau JSON contenant les données.
     * @return Un tableau comportant les noms d'espèces lus.
     */
    public static ArrayList<String> parseScientificNameJsonArray(JSONArray jsonRoot) {
        ArrayList<String> ret = new ArrayList<>();

        for (int i = 0; i < jsonRoot.length(); i++)
            ret.add(jsonRoot.getJSONObject(i).getString("scientificName"));
        return ret;
    }

    /**
     * Renvoie un tableau comportant les noms d'espèces se trouvant dans un objet JSON.
     * @param jsonObject L'objet JSON contenant les données.
     * @return Un tableau comportant les noms d'espèces lus.
     */
    public static ArrayList<String> parseScientificNameJsonObject(JSONObject jsonObject) {
        ArrayList<String> ret = new ArrayList<>();
        JSONArray jsonRoot = jsonObject.getJSONArray("results");

        for (int i = 0; i < jsonRoot.length(); i++) {
            String name = jsonRoot.getJSONObject(i).getString("scientificName");
            boolean res = true;
            for (String s : ret) {
                if (s.equals(name)) {
                    res = false;
                    break;
                }
            }
            if (res)
                ret.add(name);
        }
        return ret;
    }

    /**
     * Renvoie un tableau d'ObservationDetails à partir des données d'observation dans un objet JSON.
     * @param jsonObject L'objet JSON contenant les données d'observation.
     * @return Un tableau comportant les données d'observation.
     */
    public static ArrayList<ObservationDetails> parseObservationDetailsJsonObject(JSONObject jsonObject) {
        ArrayList<ObservationDetails> ret = new ArrayList<>();
        JSONArray jsonRoot = jsonObject.getJSONArray("results");

        for (int i = 0; i < jsonRoot.length(); i++) {
            String name = "";
            String order = "";
            String superclass = "";
            String recordedBy = "";
            String species = "";
            String bathymetry = "";
            String shoreDistance = "";
            String eventDate = "";

            try {
                name = jsonRoot.getJSONObject(i).getString("scientificName");
            } catch (JSONException ignored) {
            }
            try {
                order = jsonRoot.getJSONObject(i).getString("order");
            } catch (JSONException ignored) {
            }
            try {
                superclass = jsonRoot.getJSONObject(i).getString("superclass");
            } catch (JSONException ignored) {
            }
            try {
                recordedBy = jsonRoot.getJSONObject(i).getString("recordedBy");
            } catch (JSONException ignored) {
            }
            try {
                species = jsonRoot.getJSONObject(i).getString("scientificName");
            } catch (JSONException ignored) {
            }
            try {
                bathymetry = jsonRoot.getJSONObject(i).getString("scientificName");
            } catch (JSONException ignored) {
            }
            try {
                species = jsonRoot.getJSONObject(i).getString("scientificName");
            } catch (JSONException ignored) {
            }
            try {
                species = jsonRoot.getJSONObject(i).getString("scientificName");
            } catch (JSONException ignored) {
            }

            ret.add(new ObservationDetails(name, order, superclass, recordedBy, species, bathymetry, shoreDistance, eventDate));
        }
        return ret;
    }
}
