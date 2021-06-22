package model;

import java.util.ArrayList;

public class SpecieFeature {
    /**
     * Nom de l'espèce observée.
     */
    private final String name;
    /**
     * Degré de précision des geoHashs.
     */
    private final int geoHashPrecision;
    /**
     * Liste des observations de l'espèce.
     */
    private final ArrayList<Feature> featureList;
    /**
     * Nombre minimal non nul d'individus sur un geoHash.
     */
    private int minOccurrence;
    /**
     * Nombre maximal d'individus sur un geoHash.
     */
    private int maxOccurrence;

    public SpecieFeature(String name, ArrayList<Feature> featureList, int geoHashPrecision) {
        this.geoHashPrecision = geoHashPrecision;
        this.name = name;
        this.featureList = new ArrayList<>();
        for (Feature feature : featureList)
            this.featureList.add(new Feature(feature.getCoordinates(), feature.getN()));
        if (!generateGeoHash())
            System.out.println("[SpecieFeature]: Could not generate geoHash.");
        else
            generateMinMax();
    }

    /**
     * Renvoie un booléen à VRAI si le degré de précision du geoHash est valide.
     *
     * @param geoHashPrecision Le degré de précision du geoHash.
     * @return Un booléen.
     */
    public static boolean isGeoHashValid(int geoHashPrecision) {
        return geoHashPrecision > 0 && geoHashPrecision < 12;
    }

    /**
     * Renvoie le nom scientifique de l'espèce.
     *
     * @return Le membre name.
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie le nombre de geoHashs différentes.
     *
     * @return Le nombre de geoHashs.
     */
    public int getNbZones() {
        return featureList.size();
    }

    /**
     * Renvoie le nombre d'individus observés.
     *
     * @return Le nombre total d'individus.
     */
    public int getNbIndividuals() {
        int i = 0;
        for (Feature f : featureList)
            i += f.getN();
        return i;
    }

    /**
     * Renvoie la plus petite occurrence non nulle d'une zone.
     *
     * @return Le membre minOccurrence.
     */
    public int getMinOccurrence() {
        return minOccurrence;
    }

    /**
     * Renvoie la plus grande occurrence d'une zone.
     *
     * @return Le membre maxOccurrence.
     */
    public int getMaxOccurrence() {
        return maxOccurrence;
    }

    /**
     * Renvoie la liste des observations.
     *
     * @return Le membre featureList.
     */
    public ArrayList<Feature> getFeatureList() {
        return featureList;
    }

    /**
     * Renvoie le nombre d'occurrences pour un geoHash.
     *
     * @param geoHash La zone désirée.
     * @return Le nombre d'occurrences dans cette zone.
     */
    public int getOccurrences(String geoHash) {
        for (Feature feature : featureList) {
            if (feature.getGeoHash().equals(geoHash)) {
                return feature.getN();
            }
        }
        return 0;
    }

    /**
     * Renvoie la densité d'individus relative au nombre minimal d'entité non nul et au nombre maximal d'entité.
     *
     * @param geoHash La zone désirée.
     * @return Un pourcentage.
     */
    public float getZoneDensity(String geoHash) {
        float min = (float) minOccurrence;
        float max = (float) maxOccurrence;
        if (min == max)
            return 0;
        else
            return (getOccurrences(geoHash) - min) / (max - min);
    }

    /**
     * Renvoie l'échelle du gradient de la densité de la zone.
     *
     * @param geoHash La zone désirée.
     * @return Un gradient correspondant.
     */
    public int getZoneDensityLevel(String geoHash) {
        int ret = (int) (getZoneDensity(geoHash) * 8);
        return Math.min(ret, 7);
    }

    /**
     * Génère les geoHash des observations.
     *
     * @return Un booléen à VRAI si la génération s'est effectuée avec succès, à FAUX sinon.
     */
    private boolean generateGeoHash() {
        if (isGeoHashValid(geoHashPrecision)) {
            for (Feature f : featureList)
                f.generateGeoHash(geoHashPrecision);
            return true;
        } else
            return false;
    }

    /**
     * Génère le nombre d'occurrence minimal et maximal sur l'échantillon.
     */
    private void generateMinMax() {
        if (featureList.size() > 0) {
            int min = featureList.get(0).getN();
            int max = featureList.get(0).getN();
            for (Feature f : featureList) {
                min = Integer.min(f.getN(), min);
                max = Integer.max(f.getN(), max);
            }
            minOccurrence = min;
            maxOccurrence = max;
        }
    }
}
