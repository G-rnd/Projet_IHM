package data;

import model.Feature;
import model.SpecieFeature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FileReader {

    public static JSONObject readJsonFromUrl(String url) {
        String json = "";
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            json = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject(json);


    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static ArrayList<SpecieFeature> readLocalFile(String file) {
        try (Reader reader = new java.io.FileReader(file)) {
            BufferedReader rd = new BufferedReader(reader);
            String jsonText = readAll(rd);

            JSONArray jsonRoot = new JSONObject(jsonText).getJSONArray("features");

            ArrayList<Feature> features = new ArrayList<Feature>();
            for (int i = 0; i < jsonRoot.length(); i++) {
                //System.out.println(jsonRoot.getJSONObject(i).getString("type"));
                features.add(new Feature(jsonRoot.getJSONObject(i).getJSONObject("properties").getInt("n"),
                        getFloatArray(jsonRoot.getJSONObject(i).getJSONObject("geometry").
                                getJSONArray("coordinates").getJSONArray(0))
                ));
            }

            SpecieFeature specieFeature = new SpecieFeature(1, features);
            ArrayList<SpecieFeature> specieFeatureArrayList = new ArrayList<>();
            specieFeatureArrayList.add(specieFeature);

            return specieFeatureArrayList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Float> getFloatArray(JSONArray jsonArray) {
        ArrayList<Float> list = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            for(int j = 0; j < 2; j++)
                try {
                    list.add(Float.parseFloat(jsonArray.getJSONArray(i).get(j).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        return list;
    }
}
