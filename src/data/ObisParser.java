package data;

import model.Feature;
import model.ObservationDetails;
import model.SpecieFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ObisParser {

    public static SpecieFeature parseOccurrencesJsonObject(JSONObject jsonObject, int geoHashPrecision) {
        return parseOccurrencesJsonObject(jsonObject, "Delphinidae", geoHashPrecision);
    }

    public static SpecieFeature parseOccurrencesJsonObject(JSONObject jsonObject, String name, int geoHashPrecision) {
        JSONArray jsonRoot = jsonObject.getJSONArray("features");

        ArrayList<Feature> features = new ArrayList<>();
        for (int i = 0; i < jsonRoot.length(); i++) {
            features.add(new Feature(parseFloatArray(jsonRoot.getJSONObject(i).getJSONObject("geometry")
                    .getJSONArray("coordinates").getJSONArray(0)))
            );
        }
        return new SpecieFeature(name, features, geoHashPrecision);
    }

    public static ArrayList<String> parseScientificNameJsonArray(JSONArray jsonRoot) {
        ArrayList<String> ret = new ArrayList<>();

        for (int i = 0; i < jsonRoot.length(); i++)
            ret.add(jsonRoot.getJSONObject(i).getString("scientificName"));
        return ret;
    }

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

    public static ArrayList<ObservationDetails> parseObservationDetailsJsonObject(JSONObject jsonObject) {
        ArrayList<ObservationDetails> ret = new ArrayList<>();
        JSONArray jsonRoot = jsonObject.getJSONArray("results");

        for (int i = 0; i < jsonRoot.length(); i++) {
            String name = "";
            String order = "";
            String superclass = "";
            String recordedBy = "";
            String species = "";

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

            ret.add(new ObservationDetails(name, order, superclass, recordedBy, species));
        }
        return ret;
    }

    public static ArrayList<Float> parseFloatArray(JSONArray jsonArray) {
        ArrayList<Float> list = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 2; j++)
                try {
                    list.add(Float.parseFloat(jsonArray.getJSONArray(i).get(j).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        return list;
    }
}
