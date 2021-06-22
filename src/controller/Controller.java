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

    /**
     * Booléan à VRAI si une requête est en cours.
     */
    private boolean query;

    /**
     * Vue à laquelle envoyer des notifications.
     */
    private ReceiveNotification view;
    private AnimationTimer animationTimer;

    public Controller(Model model) {
        this.model = model;
        query = false;
    }

    /**
     * Entité à laquelle envoyer des notifications.
     *
     * @param receiver La vue associée.
     */
    public void setReceiver(ReceiveNotification receiver) {
        view = receiver;
    }

    /**
     * Renvoie l'état du contrôleur.
     *
     * @return Le membre query.
     */
    public boolean getQueryState() {
        return query;
    }

    /**
     * Permet de charger les occurrences d'une espèce.
     *
     * @param name Le nom de l'espèce.
     */
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

    /**
     * Permet de charger les occurrences d'une espèce entre 2 dates.
     *
     * @param name      Le nom de l'espèce.
     * @param startDate La date de début.
     * @param endDate   La date de fin.
     */
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

    /**
     * Permet de charger les occurrences d'une espèce sur plusieurs intervalles de temps.
     *
     * @param name      Le nom de l'espèce.
     * @param startDate La date de début.
     * @param years     Le nombre d'année.
     */
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

    /**
     * Charge les noms d'espèces présents sur un geohash.
     *
     * @param geoHash Le geohash.
     */
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

    /**
     * Renvoie le membre specie du modèle.
     *
     * @return Le membre specie du modèle.
     */
    public SpecieFeature getSpecie() {
        return model.getSpecie();
    }

    /**
     * Charge les noms d'espèces commençant par "letters".
     *
     * @param letters Les premières lettres du nom d'espèce.
     */
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

    /**
     * Charge le fichier local.
     */
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

    /**
     * Charge les détails d'observations de l'espèce en cours sur un geoHash.
     * @param geoHash Le geoHash considéré.
     * @param name Le nom de l'espèce actuelle.
     */
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

    /**
     * Renvoie le membre species du modèle.
     * @return Le membre species du modèle.
     */
    public ArrayList<SpecieFeature> getSpecies() {
        return model.getSpecies();
    }

    /**
     * Renvoie les noms d'espèces lus.
     * @return Les noms d'espèces lus.
     */
    public ArrayList<String> getNames() {
        return model.getNames();
    }

    /**
     * Renvoie les détails d'observation lus.
     * @return Les détails d'observation lus.
     */
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
