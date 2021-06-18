package model;

import data.FileReader;
import data.ObisReader;

import java.util.ArrayList;

public class Model {
    /**
     * Une SpecieFeature comportant les données d'observations.
     */
    private SpecieFeature specie;

    /**
     * Un tableau de SpecieFeature comportant les données d'observations sur plusieurs intervalles
     */

    private ArrayList<SpecieFeature> species;

    /**
     * Un tableau d'ObservationDetails composé les détails d'observations.
     */
    private ArrayList<ObservationDetails> observationDetails;

    /**
     * Un tableau de String composé de noms d'espèces.
     */
    private ArrayList<String> names;

    public Model(String filename) {
        observationDetails = new ArrayList<>();
        names = new ArrayList<>();
        species = new ArrayList<>();
        loadLocalFile(filename);
    }

    /**
     * Retourne les observations de l'espèce.
     *
     * @return La liste des observations
     */
    public SpecieFeature getSpecie() {
        return specie;
    }

    /**
     * Retourne la liste des listes d'observations d'espèce.
     *
     * @return La liste des listes d'observations.
     */
    public ArrayList<SpecieFeature> getSpecies() {
        return species;
    }

    /**
     * Renvoie les noms scientifiques lus au préalable sur Obis.
     *
     * @return Le membre names.
     */
    public ArrayList<String> getNames() {
        return names;
    }

    /**
     * Renvoie Les détails d'observation lus au préalable sur Obis.
     *
     * @return Le membre observationDetails.
     */
    public ArrayList<ObservationDetails> getObservationDetails() {
        return observationDetails;
    }

    /**
     * Charge le fichier local d'observations.
     *
     * @param name Le nom du fichier.
     */
    public void loadLocalFile(String name) {
        specie = FileReader.readLocalFile(name);
    }

    /**
     * Charge le fichier lu suite à une requête Obis, en passant en paramètre le nom de l'espèce et la précision du
     * geoHash.
     *
     * @param name             Le nom de l'espèce.
     * @param geoHashPrecision La précision du geoHash.
     */
    public void loadObisFile(String name, int geoHashPrecision) {
        if (SpecieFeature.isGeoHashValid(geoHashPrecision))
            specie = ObisReader.loadOccurrencesFile(name, geoHashPrecision);
    }

    /**
     * Charge un fichier lu suite à une requête Obis, en passant en paramètre le nom de l'espèce, la précision du
     * geoHash, la date de début et la date de fin des observations.
     *
     * @param name             Le nom de l'espèce.
     * @param geoHashPrecision La précision du geoHash.
     * @param startDate        La date de début des observations au format yyyy-MM-dd.
     * @param endDate          La date de fin des observations au format yyyy-MM-dd.
     */
    public void loadObisFile(String name, int geoHashPrecision, String startDate, String endDate) {
        if (SpecieFeature.isGeoHashValid(geoHashPrecision))
            specie = ObisReader.loadOccurrencesFile(name, geoHashPrecision, startDate, endDate);
    }

    /**
     * Charge les fichiers lus suite à une requête Obis, en passant en paramètre le de l'espèce, la précision
     * du geoHash, la date de début, le temps entre deux intervalles et le nombre d'intervalles.
     *
     * @param name             Le nom de l'espèce.
     * @param geoHashPrecision Le degré de précision du geoHash.
     * @param startDate        La date de début.
     * @param timeInterval     Le temps entre deux intervalles.
     * @param nbIntervals      Le nombre d'intervalles.
     */
    public void loadObisFiles(String name, int geoHashPrecision, String startDate, int timeInterval, int nbIntervals) {
        if (SpecieFeature.isGeoHashValid(geoHashPrecision))
            species = ObisReader.loadOccurrencesFiles(name, geoHashPrecision, startDate, timeInterval, nbIntervals);
    }

    /**
     * Charge les noms d'espèces lus suite à une requête Obis, en passant en paramètre les premières lettres des noms
     * d'espèces.
     *
     * @param initialLetters Les première lettres des noms scientifiques des espèces.
     */
    public void loadNamesFromLetters(String initialLetters) {
        names = ObisReader.loadNamesFromLetters(initialLetters);
    }

    /**
     * Charge, à la suite d'une requête Obis, les noms d'espèces présentes sur un geoHash.
     *
     * @param geoHash Le geoHash sur lequel des espèces ont été observées au moins une fois.
     */
    public void loadNamesFromGeoHash(String geoHash) {
        names = ObisReader.loadNamesFromGeoHash(geoHash);
    }

    /**
     * Charge les détails d'observations lus pour un geoHash donné, suite à une requête Obis.
     *
     * @param geoHash Le geoHash sur lequel des détails d'observations ont été rédigés.
     */
    public void loadObservationDetails(String geoHash) {
        observationDetails = ObisReader.loadObservationDetails(geoHash);
    }

    /**
     * Charge les détails d'observations lus pour un geoHash donné et pour une espèce donnée, suite à une requête Obis.
     *
     * @param geoHash Le geoHash sur lequel des détails ont été rédigés concernant l'espèce demandée.
     * @param name    Le nom de l'espèce demandée.
     */
    public void loadObservationDetails(String geoHash, String name) {
        observationDetails = ObisReader.loadObservationDetails(geoHash, name);
    }
}
