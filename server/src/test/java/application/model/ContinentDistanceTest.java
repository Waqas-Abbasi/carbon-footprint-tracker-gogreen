package application.model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ContinentDistanceTest {

    ContinentDistance mock = Mockito.mock(ContinentDistance.class);
    ContinentDistance helper = new ContinentDistance(1, 2, 50.0);

    @Test
    public void getID(){
        Mockito.when(mock.getId()).thenReturn(23);
        assertEquals(23, mock.getId());
    }
    @Test
    public void getFrom() {
        assertEquals(1, (int) helper.getFrom());
    }

    @Test
    public void getTo() {
        assertEquals(2, (int) helper.getTo());
    }

    @Test
    public void getScore() {
        assertEquals(50.0, helper.getScore(), 0);
    }
}