import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.Model;
import view.View;
import controller.Controller;

public class Main extends Application {
    /*
--module-path
"C:\Program Files (x86)\Java\javafx-sdk-16\lib"
--add-modules
javafx.controls,javafx.fxml
     */
    public static void main(String[] args) {
        launch(args);
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
