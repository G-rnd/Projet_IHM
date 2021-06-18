package model;

public class ObservationDetails {
    /**
     * Nom scientifique de l'espèce observée.
     */
    private final String scientificName;
    /**
     * Ordre de l'espèce observée.
     */
    private final String order;
    /**
     * Superclasse de l'espèce observée.
     */
    private final String superclass;
    /**
     * Groupe
     */
    private final String recordedBy;
    private final String specie;
    private final String bathymetry;
    private final String shoreDistance;
    private final String eventDate;

    public ObservationDetails(String scientificName, String order, String superclass, String recordedBy, String specie,
                              String bathymetry, String shoreDistance, String eventDate) {
        this.scientificName = scientificName;
        this.order = order;
        this.superclass = superclass;
        this.recordedBy = recordedBy;
        this.specie = specie;
        this.bathymetry = bathymetry;
        this.shoreDistance = shoreDistance;
        this.eventDate = eventDate;
    }

    /**
     * Renvoie le nom scientifique.
     *
     * @return Le membre scientificName.
     */
    public String getScientificName() {
        return scientificName;
    }

    /**
     * Renvoie l'ordre
     *
     * @return Le membre ordre.
     */
    public String getOrder() {
        return order;
    }

    /**
     * Renvoie la superclasse.
     *
     * @return Le membre superclass;
     */
    public String getSuperclass() {
        return superclass;
    }

    /**
     * Renvoie la personne ayant fait l'observation.
     *
     * @return Le membre recordedBy.
     */
    public String getRecordedBy() {
        return recordedBy;
    }

    /**
     * Renvoie le membre specie.
     *
     * @return Le membre specie.
     */
    public String getSpecie() {
        return specie;
    }

    /**
     * Renvoie le membre bathymetry.
     *
     * @return Le membre bathymetry.
     */
    public String getBathymetry() {
        return bathymetry;
    }

    /**
     * Renvoie le membre shoreDistance.
     *
     * @return Le membre shoreDistance.
     */
    public String getShoreDistance() {
        return shoreDistance;
    }

    /**
     * Renvoie le membre eventDate.
     *
     * @return Le membre eventDate.
     */
    public String getEventDate() {
        return eventDate;
    }
}
