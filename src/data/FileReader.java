package data;

import model.Feature;
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
        while((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public ArrayList<Feature> main(String[] args) {
        try (Reader reader = new java.io.FileReader("data.json")) {
            BufferedReader rd = new BufferedReader(reader);
            String jsonText = readAll(rd);
            JSONObject jsonRoot = new JSONObject(jsonText);

            ArrayList<Feature> features = new ArrayList<Feature>();


            return features;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
