package controller;

import javafx.animation.AnimationTimer;
import model.Model;
import model.ObservationDetails;
import model.SpecieFeature;
import view.DoNotification;
import view.ReceiveNotification;

import java.util.ArrayList;

public class Controller implements DoNotification {
    /**
     * Modèle associé au contrôler.
     */
    private final Model model;
    private boolean query;
    private ReceiveNotification view;

    private AnimationTimer animationTimer;

    public Controller(Model model) {
        this.model = model;
        query = false;
    }

    public void setReceiver(ReceiveNotification receiver) {
        view = receiver;
    }

    public boolean getQueryState() {
        return query;
    }

    public void loadObisFiles(String name) {
        if (!query) {
            animationTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    query = true;
                    model.loadObisFile(name.replaceAll(" ", "%20"), 3);
                    query = false;
                    notifyLoadedObis1(view);
                    stop();
                }
            };
            animationTimer.start();
        }
    }

    public void loadObisFiles(String name, String startDate, String endDate) {
        if (!query) {
            animationTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    query = true;
                    model.loadObisFile(name.replaceAll(" ", "%20"), 3, startDate, endDate);
                    query = false;
                    notifyLoadedObis2(view);
                    stop();
                }
            };
            animationTimer.start();
        }
    }

    public void loadObisFiles(String name, String startDate, int years) {
        if (!query) {
            animationTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    query = true;
                    model.loadObisFiles(name.replaceAll(" ", "%20"), 3, startDate,
                            5 * 365, years / 5);
                    query = false;
                    notifyLoadedObis3(view);
                    stop();
                }
            };
            animationTimer.start();
        }
    }

    public void loadNamesFromGeoHash(String geoHash) {
        if (!query) {
            animationTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    query = true;
                    model.loadNamesFromGeoHash(geoHash);
                    query = false;
                    notifyLoadedNames1(view);
                    stop();
                }
            };
            animationTimer.start();
        }
    }

    public SpecieFeature getSpecie() {
        return model.getSpecie();
    }

    public void loadNamesFromLetters(String letters) {
        if (!query) {
            animationTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    query = true;
                    model.loadNamesFromLetters(letters.replaceAll(" ", "%20"));
                    query = false;
                    notifyLoadedNames2(view);
                    stop();
                }
            };
            animationTimer.start();
        }
    }

    public void loadLocalFile() {
        if (!query) {
            animationTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    query = true;
                    model.loadLocalFile("data.json");
                    query = false;
                    notifyLoadedLocal(view);
                    stop();
                }
            };
            animationTimer.start();
        }
    }

    public void loadObservationsDetails(String geoHash, String name) {
        if (!query) {
            animationTimer = new AnimationTimer() {
                public void handle(long currentNanoTime) {
                    query = true;
                    model.loadObservationDetails(geoHash, name);
                    query = false;
                    notifyLoadedODS(view);
                    stop();
                }
            };
            animationTimer.start();
        }
    }

    public ArrayList<SpecieFeature> getSpecies() {
        return model.getSpecies();
    }

    public ArrayList<String> getNames() {
        return model.getNames();
    }

    public ArrayList<ObservationDetails> getObservationDetails() {
        return model.getObservationDetails();
    }

    @Override
    public void notifyLoadedObis1(ReceiveNotification receiver) {
        receiver.receiveNotificationLoadedObis1();
    }

    @Override
    public void notifyLoadedObis2(ReceiveNotification receiver) {
        receiver.receiveNotificationLoadedObis2();
    }

    @Override
    public void notifyLoadedObis3(ReceiveNotification receiver) {
        receiver.receiveNotificationLoadedObis3();
    }

    @Override
    public void notifyLoadedNames1(ReceiveNotification receiver) {
        receiver.receiveNotificationLoadedNames1();
    }

    @Override
    public void notifyLoadedNames2(ReceiveNotification receiver) {
        receiver.receiveNotificationLoadedNames2();
    }

    @Override
    public void notifyLoadedLocal(ReceiveNotification receiver) {
        receiver.receiveNotificationLoadedLocal();
    }

    @Override
    public void notifyLoadedODS(ReceiveNotification receiver) {
        receiver.receiveNotificationLoadedODS();
    }
}
