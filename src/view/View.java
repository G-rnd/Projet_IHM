package view;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import controller.Controller;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Affine;
import model.Feature;
import model.GeoHash;
import model.ObservationDetails;
import model.SpecieFeature;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View implements Initializable, ReceiveNotification {
    public static final String[] huesColors = new String[]{
            "FFE7C3", "FFFFAE", "FFDA6E", "FFB33F", "FF8D2E", "FF4D1A", "E61509", "B30021"
    };
    public static final double[] huesColorsDouble = new double[]{
            1.0, 1.0 * 0xE7 / 0xFF, 1.0 * 0xC3 / 0xFF,
            1.0, 1.0, 1.0 * 0xAE / 0xFF,
            1.0, 1.0 * 0xDA / 0xFF, 1.0 * 0x6E / 0xFF,
            1.0, 1.0 * 0xB3 / 0xFF, 1.0 * 0x3F / 0xFF,
            1.0, 1.0 * 0x8D / 0xFF, 1.0 * 0x2D / 0xFF,
            1.0, 1.0 * 0x4D / 0xFF, 1.0 * 0x1A / 0xFF,
            1.0 * 0xE6 / 0xFF, 1.0 * 0x15 / 0xFF, 1.0 * 0x09 / 0xFF,
            1.0 * 0xB3 / 0xFF, 0.0, 1.0 * 0x21 / 0xFF
    };
    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;
    /**
     * Contrôleur associé à la vue.
     */
    private final Controller controller;
    @FXML
    private Pane earthPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label nameLabel;
    @FXML
    private ListView nameList;
    @FXML
    private CheckBox timeCheckBox;
    @FXML
    private VBox timeIntervalBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Label startDateLabel;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label endDateLabel;
    @FXML
    private CheckBox evolutionCheckBox;
    @FXML
    private HBox buttonBox;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private VBox observationBox;
    @FXML
    private ListView obsCommonList;
    @FXML
    private ListView obsDetailsList;
    @FXML
    private CheckBox displayCheckBox;
    @FXML
    private Button clearButton;
    @FXML
    private HBox hues;
    @FXML
    private Pane hue0;
    @FXML
    private Pane hue1;
    @FXML
    private Pane hue2;
    @FXML
    private Pane hue3;
    @FXML
    private Pane hue4;
    @FXML
    private Pane hue5;
    @FXML
    private Pane hue6;
    @FXML
    private Pane hue7;
    @FXML
    private Label minLabel;
    @FXML
    private Label maxLabel;
    @FXML
    private Label infoLabel;

    private boolean animate;
    private boolean stopped;
    private int id_frame;
    private boolean display;
    private AnimationTimer animation;

    private long timeAnimation;
    private Group geoHashes;

    public View(Controller controller) {
        this.controller = controller;
    }

    public static Point3D geoCordTo3dCoords(float lat, float lon, float radius) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;

        return new Point3D(
                -Math.sin(Math.toRadians(lon_cor)) * Math.cos(Math.toRadians(lat_cor)) * radius,
                -Math.sin(Math.toRadians(lat_cor)) * radius,
                Math.cos(Math.toRadians(lon_cor)) * Math.cos(Math.toRadians(lat_cor)) * radius
        );
    }

    public static Affine lookAt(Point3D from, Point3D to, Point3D ydir) {
        Point3D zVec = to.subtract(from).normalize();
        Point3D xVec = ydir.normalize().crossProduct(zVec).normalize();
        Point3D yVec = zVec.crossProduct(xVec).normalize();
        return new Affine(xVec.getX(), yVec.getX(), zVec.getX(), from.getX(),
                xVec.getY(), yVec.getY(), zVec.getY(), from.getY(),
                xVec.getZ(), yVec.getZ(), zVec.getZ(), from.getZ());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();
        geoHashes = new Group();

        // Load geometry
        ObjModelImporter objImporter = new ObjModelImporter();
        try {
            URL modelUrl = this.getClass().getResource("Earth/earth.obj");
            objImporter.read(modelUrl);
        } catch (ImportException e) {
            // handle exception
            System.out.println(e.getMessage());
        }

        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);
        root3D.getChildren().add(earth);

        //Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);

        // Create scene
        SubScene subScene = new SubScene(root3D, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setCamera(camera);
        subScene.setFill(Color.gray(0.3));
        earthPane.getChildren().addAll(subScene);

        // build camera manager
        new CameraManager(camera, earthPane, root3D);

        hues.toFront();
        hue0.setStyle(" -fx-background-color: #" + huesColors[0] + ";");
        hue1.setStyle(" -fx-background-color: #" + huesColors[1] + ";");
        hue2.setStyle(" -fx-background-color: #" + huesColors[2] + ";");
        hue3.setStyle(" -fx-background-color: #" + huesColors[3] + ";");
        hue4.setStyle(" -fx-background-color: #" + huesColors[4] + ";");
        hue5.setStyle(" -fx-background-color: #" + huesColors[5] + ";");
        hue6.setStyle(" -fx-background-color: #" + huesColors[6] + ";");
        hue7.setStyle(" -fx-background-color: #" + huesColors[7] + ";");

        AmbientLight light = new AmbientLight(Color.WHITE);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        nameLabel.setVisible(false);
        timeIntervalBox.setVisible(false);
        buttonBox.setVisible(false);
        observationBox.setVisible(false);

        startDateLabel.setVisible(false);
        endDateLabel.setVisible(false);

        nameList.setVisible(false);
        nameList.setMaxHeight(0);

        animate = false;
        stopped = true;
        id_frame = 0;
        display = false;

        infoLabel.toFront();
        loadLocalFile();

        display = true;
        earth.getChildren().addAll(geoHashes);
        launchlisteners(subScene);
    }

    private void printGeoHashesFromSpecieFeature(SpecieFeature specieFeature) {
        geoHashes.getChildren().clear();
        for (Feature f : specieFeature.getFeatureList()) {
            int index = specieFeature.getZoneDensityLevel(f.getGeoHash());
            printGeoHash(geoHashes, f.getCoordinates(), Color.color(huesColorsDouble[3 * index],
                    huesColorsDouble[3 * index + 1], huesColorsDouble[3 * index + 2]));
        }
        minLabel.setText(Integer.toString(specieFeature.getMinOccurrence()));
        maxLabel.setText(Integer.toString(specieFeature.getMaxOccurrence()));
        display = true;
    }

    private void printGeoHash(Group parent, ArrayList<Float> points, Color color) {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.505));

        AddQuadrilaterial(parent,
                geoCordTo3dCoords(points.get(1), points.get(0), 1.1f),
                geoCordTo3dCoords(points.get(3), points.get(0), 1.1f),
                geoCordTo3dCoords(points.get(3), points.get(2), 1.1f),
                geoCordTo3dCoords(points.get(1), points.get(2), 1.1f),
                material
        );
    }

    private void AddQuadrilaterial(Group parent, Point3D topRight, Point3D bottomRight, Point3D bottomLeft,
                                   Point3D topLeft, PhongMaterial material) {
        final TriangleMesh triangleMesh = new TriangleMesh();
        final float[] points = {
                (float) topRight.getX(), (float) topRight.getY(), (float) topRight.getZ(),
                (float) topLeft.getX(), (float) topLeft.getY(), (float) topLeft.getZ(),
                (float) bottomLeft.getX(), (float) bottomLeft.getY(), (float) bottomLeft.getZ(),
                (float) bottomRight.getX(), (float) bottomRight.getY(), (float) bottomRight.getZ()
        };
        final float[] texCoords = {
                1, 1,
                1, 0,
                0, 1,
                0, 0
        };
        final int[] faces = {
                0, 1, 1, 0, 2, 2,
                0, 1, 2, 2, 3, 3
        };

        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoords);
        triangleMesh.getFaces().setAll(faces);

        final MeshView meshView = new MeshView(triangleMesh);
        meshView.setMaterial(material);
        parent.getChildren().addAll(meshView);
    }

    private void printAffineFromSpecieFeature(SpecieFeature specieFeature) {
        geoHashes.getChildren().clear();
        for (Feature f : specieFeature.getFeatureList()) {
            int index = specieFeature.getZoneDensityLevel(f.getGeoHash());
            float offset = (float) Math.sqrt(specieFeature.getZoneDensity(f.getGeoHash())) * 5;
            Point3D from = geoCordTo3dCoords((f.getCoordinates().get(1) + f.getCoordinates().get(3)) / 2,
                    (f.getCoordinates().get(0) + f.getCoordinates().get(2)) / 2, 1.0f + offset / 2);
            Box box = new Box(0.01f, 0.01f, offset);

            final PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(new Color(huesColorsDouble[3 * index], huesColorsDouble[3 * index + 1],
                    huesColorsDouble[3 * index + 2], 0.6));
            box.setMaterial(material);

            Point3D to = Point3D.ZERO;
            Point3D yDir = new Point3D(0, 1, 0);

            Group group = new Group();
            Affine affine = new Affine();

            affine.append(lookAt(from, to, yDir));
            group.getTransforms().setAll(affine);
            group.getChildren().addAll(box);

            geoHashes.getChildren().addAll(group);
        }
        minLabel.setText(Integer.toString(specieFeature.getMinOccurrence()));
        maxLabel.setText(Integer.toString(specieFeature.getMaxOccurrence()));
        display = true;
    }

    private void launchlisteners(SubScene subScene) {
        timeCheckBox.setOnAction(event -> {
            if (timeCheckBox.isSelected()) {
                timeIntervalBox.setVisible(true);
            } else {
                buttonBox.setVisible(false);
                timeIntervalBox.setVisible(false);
                evolutionCheckBox.setSelected(false);
            }
        });
        evolutionCheckBox.setOnAction(event -> {
            buttonBox.setVisible(evolutionCheckBox.isSelected());
            startButton.setDisable(true);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
        });

        clearButton.setOnAction(event -> {
            minLabel.setText("Min");
            maxLabel.setText("Max");
            geoHashes.getChildren().clear();
            startButton.setDisable(true);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
            buttonBox.setVisible(false);
            evolutionCheckBox.setSelected(false);
            timeIntervalBox.setVisible(false);
            timeCheckBox.setSelected(false);
            infoLabel.setVisible(false);

            nameList.setVisible(false);
            observationBox.setVisible(false);
            observationBox.setMaxHeight(0);
            obsCommonList.getItems().clear();
            obsDetailsList.getItems().clear();
            display = false;
        });

        nameTextField.setOnAction(event -> {
            nameList.getItems().clear();
            nameList.setVisible(false);
            nameList.setMaxHeight(0);

            if (!timeCheckBox.isSelected()) {
                infoLabel.setVisible(true);
                infoLabel.setText("Chargement d'une espèce ...");
                controller.loadObisFiles(nameTextField.getCharacters().toString());
                // wait receiveNotificationLoadedObis1
            } else {
                // Mode entre 2 dates.
                LocalDate startDate = startDatePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();
                if (startDate != null && endDate != null) {
                    if (startDate.isBefore(endDate)) {
                        startDateLabel.setVisible(false);
                        startDateLabel.setMaxHeight(0);
                        endDateLabel.setVisible(false);
                        endDateLabel.setMaxHeight(0);

                        if (!evolutionCheckBox.isSelected()) {
                            infoLabel.setVisible(true);
                            infoLabel.setText("Chargement d'une espèce ...");
                            controller.loadObisFiles(nameTextField.getCharacters().toString(), startDate.toString(), endDate.toString());
                            // wait receiveNotificationLoadedObis2
                        } else {
                            // mode Evolution
                            infoLabel.setVisible(true);
                            infoLabel.setText("Chargement d'une espèce ...");
                            controller.loadObisFiles(nameTextField.getCharacters().toString(), startDate.toString(),
                                    endDate.getYear() - startDate.getYear());
                            // wait receiveNotificationLoadedObis3
                        }
                    } else {
                        startDateLabel.setVisible(true);
                        startDateLabel.setMaxHeight(18);
                        endDateLabel.setVisible(true);
                        endDateLabel.setMaxHeight(18);
                    }
                } else {
                    startDateLabel.setVisible(true);
                    startDateLabel.setMaxHeight(18);
                    endDateLabel.setVisible(true);
                    endDateLabel.setMaxHeight(18);
                }
            }
        });

        subScene.setOnMouseClicked(event -> {
            Point3D point3D = event.getPickResult().getIntersectedPoint();

            double latitude = -Math.asin(point3D.getY() / 1.1) * 180 / Math.PI - TEXTURE_LAT_OFFSET;
            double longitude = -(Math.atan2(-point3D.getZ(), -point3D.getX())) - Math.PI / 2;
            if (longitude < -Math.PI)
                longitude += Math.PI * 2;

            longitude *= -180 / Math.PI;
            longitude -= TEXTURE_LON_OFFSET;

            if (!Double.isNaN(longitude) && !Double.isNaN(longitude)) {
                String geoHash = GeoHash.convertGPStoGeoHash((float) latitude, (float) longitude, 3);

                if (event.getClickCount() == 1 && event.isControlDown()) {
                    if (controller.getSpecie().getOccurrences(geoHash) > 0) {
                        String name;
                        if (evolutionCheckBox.isSelected()) {
                            try {
                                name = controller.getSpecies().get(0).getName();
                            } catch (Exception e) {
                                name = "";
                            }
                        } else {
                            try {
                                name = controller.getSpecie().getName();
                            } catch (Exception e) {
                                name = "";
                            }
                        }

                        if (!name.equals("")) {
                            infoLabel.setVisible(true);
                            infoLabel.setText("Chargement des détails d'observations ...");
                            controller.loadObservationsDetails(geoHash, name);
                            // wait receiveNotificationLoadedODS
                        }
                    }
                } else if (event.getClickCount() == 2) {
                    if (!controller.getQueryState()) {
                        nameList.getItems().clear();
                        infoLabel.setVisible(true);
                        infoLabel.setText("Chargement des noms d'espèces ...");
                        controller.loadNamesFromGeoHash(geoHash);
                        // wait receiveNotificationLoadedNames1
                    }
                }
            }
        });

        startButton.setOnAction(actionEvent -> {
            startButton.setDisable(true);
            pauseButton.setDisable(false);
            stopButton.setDisable(false);
            stopped = false;
            animate = true;
            id_frame = 0;
            timeAnimation = System.nanoTime();
            clearButton.setDisable(true);

            animation = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    double t = (currentNanoTime - timeAnimation) / 1000000000.0;
                    if (t > 1) {
                        timeAnimation = System.nanoTime();
                        infoLabel.setText("Étape " + id_frame + " ...");
                        infoLabel.setVisible(true);
                        SpecieFeature sf = controller.getSpecies().get(id_frame);
                        if (displayCheckBox.isSelected())
                            printAffineFromSpecieFeature(sf);
                        else
                            printGeoHashesFromSpecieFeature(sf);
                        minLabel.setText(Integer.toString(sf.getMinOccurrence()));
                        maxLabel.setText(Integer.toString(sf.getMaxOccurrence()));

                        id_frame++;
                        if (id_frame == controller.getSpecies().size()) {
                            geoHashes.getChildren().clear();
                            startButton.setDisable(false);
                            clearButton.setDisable(false);
                            pauseButton.setDisable(true);
                            stopButton.setDisable(false);
                            infoLabel.setVisible(false);
                            stopped = true;
                            animate = false;
                            id_frame = 0;
                            stop();
                        }
                    }
                }
            };
            animation.start();
        });

        pauseButton.setOnAction(actionEvent -> {
            clearButton.setDisable(false);
            animate = !animate;
            if (animate) {
                infoLabel.setText("");
                infoLabel.setVisible(false);
                animation.start();
            } else {
                infoLabel.setText("Pause ...");
                infoLabel.setVisible(true);
                animation.stop();
            }
        });

        stopButton.setOnAction(actionEvent -> {
            geoHashes.getChildren().clear();
            startButton.setDisable(false);
            clearButton.setDisable(false);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
            stopped = true;
            animate = false;
            id_frame = 0;
            animation.stop();
        });

        nameList.setOnMouseClicked(event -> {
            String s = nameList.getSelectionModel().getSelectedItems().get(0).toString();
            nameTextField.setText(s);
            nameList.setVisible(false);
            nameList.setMaxHeight(0);
            infoLabel.setVisible(false);
        });

        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!controller.getQueryState() && !newValue.equals("") && !oldValue.equals(newValue)) {
                nameList.getItems().clear();
                infoLabel.setVisible(true);
                infoLabel.setText("Chargement des noms d'espèces ...");
                controller.loadNamesFromLetters(newValue);
                // wait receiveNotificationLoadedNames2
            }
        });

        displayCheckBox.setOnAction(actionEvent -> {
            if (display)
                if (displayCheckBox.isSelected()) {
                    // mode 3d
                    if (!evolutionCheckBox.isSelected())
                        // on est en mode statique
                        printAffineFromSpecieFeature(controller.getSpecie());
                    else if (!stopped)
                        //on est en mode animation et l'animation est lancée.
                        printAffineFromSpecieFeature(controller.getSpecies().get(id_frame));
                } else
                    // mode 2d
                    if (!evolutionCheckBox.isSelected())
                        // on est en mode statique
                        printGeoHashesFromSpecieFeature(controller.getSpecie());
                    else if (!stopped)
                        //on est en mode animation et l'animation est lancée.
                        printGeoHashesFromSpecieFeature(controller.getSpecies().get(id_frame));

        });
    }

    private void loadLocalFile() {
        infoLabel.setVisible(true);
        infoLabel.setText("Chargement du fichier local en cours ...");
        controller.loadLocalFile();
        display = false;
        // wait receiveNotificationLoadedLocal
    }

    @Override
    public void receiveNotificationLoadedObis1() {
        infoLabel.setVisible(false);
        if (controller.getSpecie() == null) {
            nameLabel.setVisible(true);
            nameLabel.setMaxHeight(18);
        } else {
            nameLabel.setVisible(false);
            nameLabel.setMaxHeight(0);
            if (displayCheckBox.isSelected())
                printAffineFromSpecieFeature(controller.getSpecie());
            else
                printGeoHashesFromSpecieFeature(controller.getSpecie());
        }
    }

    @Override
    public void receiveNotificationLoadedObis2() {
        infoLabel.setVisible(false);
        if (controller.getSpecie() == null) {
            nameLabel.setVisible(true);
            nameLabel.setMaxHeight(18);
        } else {
            nameLabel.setVisible(false);
            nameLabel.setMaxHeight(0);
            if (displayCheckBox.isSelected())
                printAffineFromSpecieFeature(controller.getSpecie());
            else
                printGeoHashesFromSpecieFeature(controller.getSpecie());
        }
    }

    @Override
    public void receiveNotificationLoadedObis3() {
        infoLabel.setVisible(false);
        startButton.setDisable(false);
        animate = false;
        stopped = true;
        geoHashes.getChildren().clear();
        minLabel.setText("Min");
        maxLabel.setText("Max");
    }

    @Override
    public void receiveNotificationLoadedNames1() {
        ArrayList<String> namesArray = controller.getNames();
        if (namesArray.size() > 0) {
            infoLabel.setVisible(false);
            ObservableList<String> names = FXCollections.observableArrayList(namesArray);
            nameList.setItems(names);
            nameList.setVisible(true);
            nameList.setMaxHeight(80);
        } else {
            infoLabel.setVisible(true);
            infoLabel.setText("Aucune espèce pour ce geoHash ...");
        }
    }

    @Override
    public void receiveNotificationLoadedNames2() {
        ArrayList<String> namesArray = controller.getNames();
        ObservableList<String> names = FXCollections.observableArrayList(namesArray);
        nameList.setItems(names);
        if (names.size() > 1) {
            infoLabel.setVisible(false);
            nameList.setVisible(true);
            nameList.setMaxHeight(80);
        } else if (names.size() == 0) {
            infoLabel.setVisible(true);
            infoLabel.setText("Aucune espèce trouvée commençant par " + nameTextField.getCharacters().toString() + " ...");
        }
    }

    @Override
    public void receiveNotificationLoadedLocal() {
        if (controller.getSpecie() != null && controller.getSpecie().getFeatureList().size() > 0) {
            nameTextField.setText("Delphinidae");
            printGeoHashesFromSpecieFeature(controller.getSpecie());
            display = true;
            infoLabel.setVisible(false);
        } else {
            infoLabel.setVisible(true);
            infoLabel.setText("Chargement du fichier local impossible ...");
        }
    }

    @Override
    public void receiveNotificationLoadedODS() {
        ArrayList<ObservationDetails> observationDetails = controller.getObservationDetails();
        if (observationDetails.size() > 0) {
            infoLabel.setVisible(false);
            observationBox.setVisible(true);
            observationBox.setMaxHeight(80);
            obsCommonList.getItems().clear();
            obsDetailsList.getItems().clear();

            obsCommonList.setVisible(true);
            obsDetailsList.setVisible(true);

            ArrayList<String> commonList = new ArrayList<>();
            ArrayList<String> detailsList = new ArrayList<>();

            ObservationDetails od = observationDetails.get(0);
            commonList.add("Attributs communs :");
            commonList.add("Superclasse : " + od.getSuperclass());

            detailsList.add("Attributs spécifiques:");
            for (ObservationDetails o : observationDetails) {
                detailsList.add("----------");
                detailsList.add("Nom scientifique : " + o.getScientificName());
                detailsList.add("Espèce : " + o.getSpecie());
                detailsList.add("Ordre : " + od.getOrder());
                if (!o.getBathymetry().equals(""))
                    detailsList.add("Bathymetry : " + o.getBathymetry());
                if (!o.getShoreDistance().equals(""))
                    detailsList.add("Shore distance : " + o.getShoreDistance());
                if (!o.getEventDate().equals(""))
                    detailsList.add("Event date : " + o.getEventDate());
                if (!o.getRecordedBy().equals(""))
                    detailsList.add("Recorded by : " + o.getRecordedBy());
            }
            obsCommonList.setItems(FXCollections.observableArrayList(commonList));
            obsDetailsList.setItems(FXCollections.observableArrayList(detailsList));
        } else {
            infoLabel.setText("Il n'y avait pas de détails d'observations ...");
            infoLabel.setVisible(false);
        }
    }
}
