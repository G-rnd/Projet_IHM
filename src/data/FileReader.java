package data;

import model.SpecieFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class FileReader {

    /**
     * @param rd Reader
     * @return Les données lues;
     * @throws IOException -
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Récupère le nombre de signalements par région pour un fichier local.
     *
     * @param file Le fichier local.
     * @return Une SpecieFeature avec les informations lues.
     */
    public static SpecieFeature readLocalFile(String file) {
        try (Reader reader = new java.io.FileReader(file)) {
            BufferedReader rd = new BufferedReader(reader);
            String jsonText = readAll(rd);

            return ObisParser.parseOccurrencesJsonObject(new JSONObject(jsonText), "Delphinidae", 3);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Renvoie un JSONObject obtenu après une requête.
     *
     * @param url L'url de la requête.
     * @return Le JSONObject lu.
     */
    public static JSONObject readJsonObjectFromUrl(String url) {
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

    /**
     * Renvoie un JSONArray obtenu après une requête.
     *
     * @param url L'url de la requête.
     * @return Le JSONArray lu.
     */
    public static JSONArray readJsonArrayFromUrl(String url) {
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
        return new JSONArray(json);
    }
}
