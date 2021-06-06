import controller.Controller;
import data.ObisReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Feature;
import model.GeoHash;
import model.Model;
import model.SpecieFeature;
import view.View;

import java.util.ArrayList;

public class Main extends Application {
    /*
--module-path
"C:\Program Files (x86)\Java\javafx-sdk-16\lib"
--add-modules
javafx.controls,javafx.fxml
     */
    public static void main(String[] args) {
        //launch(args);

/*
        SpecieFeature specieFeature = Model.loadObisFile("Delphinidae", 3, "2018-12-02",
                "2021-05-01");
        System.out.println(specieFeature.getName());
        System.out.println(specieFeature.getFeatureList().get(0).getZone().toString());
        System.out.println(specieFeature.getFeatureList().size());
        System.out.println(specieFeature.getOccurrences("",3));
        System.out.println(specieFeature.getMinOccurrences());
        System.out.println(specieFeature.getMaxOccurrences());

 */
        int test = 0b0000;

        Model model = new Model("data.json");
        if ((test & 0b1) == 1) {
            for (ArrayList<Float> zone : model.getAllCoordinates())
                System.out.println(GeoHash.convertCoordinatesToGeoHash(zone, 3));
        }

        if ((test & 0b10) == 0b10) {
            System.out.println("--*--");
            model.loadNamesFromLetters("Del");
            for (String s : model.getNames())
                System.out.println(s);
        }

        if ((test & 0b100) == 0b100) {
            System.out.println("--*--");
            ArrayList<String> species = ObisReader.loadNamesFromGeoHash("spd");
            for (String s : species)
                System.out.println(s);
        }

        if ((test & 0b1000) == 0b1000) {
            System.out.println("--*--");
            model.loadObisFile("Delphinidae", 3);
            System.out.println("Min: " + model.getMinOccurrence());
            System.out.println("Max: " + model.getMaxOccurrence());
            System.out.println("Density: " + model.getDensity("spd"));
        }

        System.out.println("--------");
        model.loadObisFile("Delphinidae", 3, "1980-05-02", "2020-01-22");
        SpecieFeature sf = model.getSpecie();

        /*
        for(Feature f : sf.getFeatureList())
            System.out.println(f.getGeoHash());
         */
        System.out.println("Min: " + model.getMinOccurrence());
        System.out.println("Max: " + model.getMaxOccurrence());
        System.out.println("Occ: "+ model.getOccurrence("585"));
        System.out.println("Rho: "+ model.getDensity("585"));
        System.out.println("-----\nEND");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //On crée le modèle
        Model model = new Model("data.json");

        //On crée le contrôleur
        Controller controller = new Controller(model);

        //On crée la vue, qui aura le contrôle du fichier FXML
        View view = new View(controller);

        try {
            //On prépare le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("window.fxml"));

            //On définit la vue comme contrôleur de ce fichier
            loader.setController(view);

            //On charge le fichier FXML, il appellera la méthode *initialize()* de la vue
            Parent root = loader.load();

            //On crée la scène
            Scene scene = new Scene(root);

            //On définit cette scène comme étant la scène de notre première fenêtre
            primaryStage.setScene(scene);

            //On rend cette fenêtre visible
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
