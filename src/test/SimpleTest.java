package test;

import model.GeoHash;
import model.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SimpleTest {
    Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model("data.json");
        assertNotEquals(model, null);
        if (model == null)
            throw new Exception();
        try {
            if (!model.getSpecie().getName().equals("Delphinidae"))
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void OccurrenceTest() {
        model.loadObisFile("Delphinidae", 3, "1980-05-02", "2020-01-22");
        assertEquals(8076, model.getSpecie().getOccurrences("djz"));
    }

    @Test
    public void YearNumberTest() {
        assertEquals(4099, model.getSpecie().getNbZones());
    }

    @Test
    public void NamesLettersTest() {
        model.loadNamesFromLetters("Del");
        assertEquals(20, model.getNames().size());
        model.loadNamesFromLetters("Selach");
        assertEquals(8, model.getNames().size());
    }

    @Test
    public void NamesGeoHashTest() {
        model.loadNamesFromGeoHash("spd");
        assertEquals(9, model.getNames().size());
    }

    @Test
    public void LoadMultipleTest() {
        model.loadObisFiles("Selachii", 3, "1950-01-01", 365*5, 10);
        assertEquals(10, model.getSpecies().size());
    }

    @Test
    public void LoadMultipleTestCompare() {
        model.loadObisFile("Selachii", 3, "1950-01-01", "1955-01-01");
        model.loadObisFiles("Selachii", 3, "1950-01-01", 365*5, 1);
        assertEquals(model.getSpecie().getMaxOccurrence(), model.getSpecies().get(0).getMaxOccurrence());
    }

    @Test
    public void NbOccurrencesTest() {
        model.loadObisFile("Mobula%20birostris", 3);
        assertEquals(2468, model.getSpecie().getNbIndividuals());
    }

    @Test
    public void NbOccurrenceMinMaxTest() {
        model.loadObisFile("Mobula%20birostris", 3);
        assertEquals(180, model.getSpecie().getMaxOccurrence());
        assertEquals(1, model.getSpecie().getMinOccurrence());
    }

    @Test
    public void ObservationDetailsTest() {
        model.loadObservationDetails("spd");
        assertEquals(10, model.getObservationDetails().size());
        model.loadObservationDetails("spd", "Mobula%20birostris");
        assertEquals(10, model.getObservationDetails().size());
    }

    @Test
    public void ObservationDetailsTest2() {
        model.loadObservationDetails("spd", "Mobula%20birostris");
        assertEquals(10, model.getObservationDetails().size());
    }

    @Test
    public void GeoHashTest() {
        assertEquals("u037m", GeoHash.convertGPStoGeoHash(47f,2f,5));
        assertEquals("gbsuv", GeoHash.convertGPStoGeoHash(48.669f,-4.329f,5));
        assertEquals("fur", GeoHash.convertGPStoGeoHash(69.6f,-45.7f,3));
        ArrayList<Float> coords = new ArrayList<>(List.of(-80.15625f,32.34375f,-78.75f,33.75f));
        assertEquals("djz", GeoHash.convertCoordinatesToGeoHash(coords, 3));
    }
}