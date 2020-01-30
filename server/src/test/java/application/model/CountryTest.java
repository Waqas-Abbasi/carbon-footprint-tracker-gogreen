package application.model;

import application.model.Continent;
import application.model.Country;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

public class CountryTest {

    Country helper = new Country(1, "The Netherlands", 7.8);
    Country mock = Mockito.mock(Country.class);
    Continent mock_continent = Mockito.mock(Continent.class);

    @Test
    public void getCountryId() {
        assertEquals(1, (int)helper.getCountryId());
    }

    @Test
    public void getName() {
        assertEquals("The Netherlands", helper.getName());
    }

    @Test
    public void getAverage_Footprint() {
        assertEquals(7.8, helper.getAverage_Footprint(),0);
    }

    @Test
    public void getContinent() {
        Mockito.when(mock.getContinent()).thenReturn(mock_continent);
        Assert.isInstanceOf(Continent.class, mock.getContinent());
    }

    @Test
    public void getContinent_id() {
        Mockito.when(mock.getContinent_id()).thenReturn(4);
        assertEquals(4, mock.getContinent_id());
    }
}