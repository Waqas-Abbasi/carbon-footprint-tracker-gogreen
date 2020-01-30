package application.model;

import application.model.Continent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContinentTest {

    Continent europe = new Continent(1, "Europe");

    @Test
    public void getContinent_id() {
        assertEquals(1, (int)europe.getContinent_id());
    }

    @Test
    public void getName() {
        assertEquals("Europe", europe.getName());
    }

}