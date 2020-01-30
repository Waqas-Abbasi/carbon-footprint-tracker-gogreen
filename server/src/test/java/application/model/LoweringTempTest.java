package application.model;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

public class LoweringTempTest {

    LoweringTemp mock = Mockito.mock(LoweringTemp.class);
    LoweringTemp helper = new LoweringTemp(23,23.0, 10.0);
    LoweringTemp helper1 = new LoweringTemp(0,null, null);
    Activity mock_activity = Mockito.mock(Activity.class);

    @Test
    public void getId(){
        Mockito.when(mock.getId()).thenReturn(56);
        assertEquals(56, mock.getId());
    }

    @Test
    public void getActivityID(){
        assertEquals(23, (int) helper.getActivityID());
    }

    @Test
    public void setActivityID(){
        helper1.setActivityID(4);
        assertEquals(4, (int) helper1.getActivityID());
    }

    @Test
    public void getTemp_Inside() {
        assertEquals(23.0, helper.getTemp_Inside(), 0);
    }

    @Test
    public void setTemp_Inside() {
        helper1.setTemp_Inside(20.0);
        assertEquals(20.0, helper1.getTemp_Inside(), 0);
    }

    @Test
    public void getTemp_Outside() {
        assertEquals(10.0, helper.getTemp_Outside(), 0);
    }

    @Test
    public void setTemp_Outside() {
        helper1.setTemp_Outside(14.2);
        assertEquals(14.2, helper1.getTemp_Outside(), 0);
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

