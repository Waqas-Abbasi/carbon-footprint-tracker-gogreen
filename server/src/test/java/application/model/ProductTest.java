package application.model;

import application.model.Activity;
import application.model.Continent;
import application.model.Product;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

public class ProductTest {

    Product mock = Mockito.mock(Product.class);
    Product helper = new Product(1, "Shampoo", 2);
    Product helper1 = new Product(null,null,null);
    Continent mock_continent = Mockito.mock(Continent.class);
    Activity mock_activity = Mockito.mock(Activity.class);

    @Test
    public void getProductID(){
        Mockito.when(mock.getProductID()).thenReturn(5);
        assertEquals(5, (int) mock.getProductID());
    }

    @Test
    public void getActivityID() {
        assertEquals(1, (int)helper.getActivityID());
    }

    @Test
    public void setActivityID() {
        helper1.setActivityID(2);
        assertEquals(2, (int)helper1.getActivityID());
    }

    @Test
    public void getName() {
        assertEquals("Shampoo", helper.getName());
    }

    @Test
    public void setName() {
        helper1.setName("Computer");
        assertEquals("Computer", helper1.getName());
    }

    @Test
    public void getContinentID() {
        assertEquals(2, (int)helper.getContinentID());
    }

    @Test
    public void setContinentID() {
        helper1.setContinentID(3);
        assertEquals(3, (int)helper1.getContinentID());
    }

    @Test
    public void getActivity() {
        Mockito.when(mock.getActivity()).thenReturn(mock_activity);
        Assert.isInstanceOf(Activity.class, mock.getActivity());
    }

    @Test
    public void setActivity() {
        helper.setActivity(mock_activity);
        Assert.isInstanceOf(Activity.class, helper.getActivity());
    }

    @Test
    public void getContinent() {
        Mockito.when(mock.getContinent()).thenReturn(mock_continent);
        Assert.isInstanceOf(Continent.class, mock.getContinent());
    }

    @Test
    public void setContinent() {
        helper.setContinent(mock_continent);
        Assert.isInstanceOf(Continent.class, helper.getContinent());
    }
}