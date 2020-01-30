package application.model;

import application.model.Food;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class FoodTest {

    Food mock = Mockito.mock(Food.class);
    Food helper = new Food("Beef", -37.1);

    @Test
    public void getFoodID() {
        Mockito.when(mock.getFoodID()).thenReturn(1);
        assertEquals(1, (int) mock.getFoodID());
    }

    @Test
    public void getName() {
        assertEquals("Beef", helper.getFoodName());
    }

    @Test
    public void getScore() {
        assertEquals(-37.1, helper.getScore(),0);
    }

}