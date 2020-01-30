package application.model;

import application.model.Activity;
import application.model.Meal;
import application.model.MealSub;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import static org.junit.Assert.assertEquals;

public class MealTest {

    Meal mock = Mockito.mock(Meal.class);
    MealSub mockSub = Mockito.mock(MealSub.class);
    Meal helper = new Meal(mockSub,2,4,5);
    Meal helper1 = new Meal(null,0,0,0);
    Activity mock_activity = Mockito.mock(Activity.class);

    @Test
    public void getId(){
        Mockito.when(mock.getID()).thenReturn(4);
        assertEquals(4, mock.getID());
    }

    @Test
    public void getMealSub(){
        assertEquals(mockSub, helper.getMealSub());
    }

    @Test
    public void setMealSub(){
        helper1.setMealSub(mockSub);
        assertEquals(mockSub, helper1.getMealSub());
    }

    @Test
    public void getMealID() {
        assertEquals(2, helper.getMealID());
    }

    @Test
    public void setMealID() {
        helper1.setMealID(1);
        assertEquals(1, helper1.getMealID());
    }

    @Test
    public void getActivityID() {
        assertEquals(4, helper.getActivityID());
    }

    @Test
    public void setActivityID() {
        helper1.setActivityID(2);
        assertEquals(2, helper1.getActivityID());
    }

    @Test
    public void getFoodID() {
        assertEquals(5, helper.getFoodID());
    }

    @Test
    public void setFoodID() {
        helper1.setFoodID(4);
        assertEquals(4, helper1.getFoodID());
    }

    @Test
    public void setMealSub1() {
        helper.setMealSub(mockSub);
        Assert.isInstanceOf(MealSub.class, helper.getMealSub());
    }

    @Test
    public void getMealSub1() {
        Mockito.when(mock.getMealSub()).thenReturn(mockSub);
        Assert.isInstanceOf(MealSub.class, mock.getMealSub());
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
}