package test;

import model.Model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SimpleTest {
    Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model("data.json");
        assertNotEquals(model, null);

        try {
            if (!model.getName().equals("Delphinidae"))
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void OccurrenceTest() {
        model.loadObisFile("Delphinidae", 5, "1980-05-02", "2020-01-22");
        assertEquals(101, model.getOccurrence("h8p80"));
    }

    @Test
    public void YearNumberTest() {
        assertEquals(4099, model.getNbFeatures());
    }

    @Test
    public void NamesLettersTest() {
        model.loadNamesFromLetters("Del");
        assertEquals(20, model.getNames().size());
    }

    @Test
    public void NamesGeoHashTest() {
        model.loadNamesFromGeoHash("spd");
        assertEquals(9, model.getNames().size());
    }

    @Test
    public void NbOccurrencesTest() {
        model.loadObisFile("Mobula%20birostris", 3);
        assertEquals(2468, model.getNbIndividuals());
    }

    @Test
    public void NbOccurrenceMinMaxTest() {
        model.loadObisFile("Mobula%20birostris", 3);
        assertEquals(180, model.getMaxOccurrence());
        assertEquals(1, model.getMinOccurrence());
    }

    @Test
    public void ObservationDetailsTest() {
        model.loadObservationDetails("spd");
        assertEquals(10, model.getObservationDetails().size());
        model.loadObservationDetails("spd", "Mobula%20birostris");
        assertEquals(3, model.getObservationDetails().size());
    }
}