package application.model;

import application.model.MealSub;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MealSubTest {

    MealSub mock = Mockito.mock(MealSub.class);

    @Test
    public void getId() {
        Mockito.when(mock.getId()).thenReturn(43);
        assertEquals(43, (int) mock.getId());
    }
}