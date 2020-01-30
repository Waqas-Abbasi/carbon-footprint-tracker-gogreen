package application.model;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

public class SolarPanelsTest {

    SolarPanels mock = Mockito.mock(SolarPanels.class);
    SolarPanels helper = new SolarPanels(534.3 , 32.4, 1);
    SolarPanels helper1 = new SolarPanels(null, null, null);
    Activity mock_activity = Mockito.mock(Activity.class);

    @Test
    public void getId(){
        Mockito.when(mock.getId()).thenReturn(6);
        assertEquals(6, (int) mock.getId());
    }

    @Test
    public void getActivityID() {
        assertEquals(1, (int) helper.getActivityID());
    }

    @Test
    public void setActivityID() {
        helper1.setActivityID(2);
        assertEquals(2, (int)helper1.getActivityID());
    }

    @Test
    public void getElectricity_Usage() {
        assertEquals(534.3, helper.getElectricity_Usage(), 0);
    }

    @Test
    public void setElectricity_Usage() {
        helper1.setElectricity_Usage(4.3);
        assertEquals(4.3, helper1.getElectricity_Usage(), 0);
    }

    @Test
    public void getPercentage_Saved() {
        assertEquals(32.4, helper.getPercentage_Saved(), 0);
    }

    @Test
    public void setPercentage_Saved() {
        helper1.setPercentage_Saved(32.9);
        assertEquals(32.9, helper1.getPercentage_Saved(), 0);
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