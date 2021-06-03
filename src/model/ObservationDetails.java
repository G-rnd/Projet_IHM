package model;

public class ObservationDetails {
    private final String scientificName;
    private final String order;
    private final String superclass;
    private final String recordedBy;
    private final String specie;

    public ObservationDetails() {
        scientificName = "";
        order = "";
        superclass = "";
        recordedBy = "";
        specie = "";
    }

    public ObservationDetails(String scientificName, String order, String superclass, String recordedBy, String specie) {
        this.scientificName = scientificName;
        this.order = order;
        this.superclass = superclass;
        this.recordedBy = recordedBy;
        this.specie = specie;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getOrder() {
        return order;
    }

    public String getSuperclass() {
        return superclass;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public String getSpecie() {
        return specie;
    }
}
