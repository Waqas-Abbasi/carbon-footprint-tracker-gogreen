package application.model;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import static org.junit.Assert.*;

public class PublicTransportTest {

    PublicTransport mock = Mockito.mock(PublicTransport.class);
    PublicTransport helper = new PublicTransport(2, 4.3,1);
    PublicTransport helper1 = new PublicTransport(null, null, null);
    Activity mock_activity = Mockito.mock(Activity.class);
    Vehicle mock_vehicle = Mockito.mock(Vehicle.class);

    @Test
    public void getId(){
        Mockito.when(mock.getId()).thenReturn(6);
        assertEquals(6, (int) mock.getId());
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
    public void getVehicleID() {
        assertEquals(2, (int)helper.getVehicleID());
    }

    @Test
    public void setVehicleID() {
        helper1.setVehicleID(4);
        assertEquals(4, (int)helper1.getVehicleID());
    }

    @Test
    public void getDistance() {
        assertEquals(4.3, helper.getDistance(), 0);
    }

    @Test
    public void setDistance() {
        helper1.setDistance(309.4);
        assertEquals(309.4, helper1.getDistance(), 0);
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
    public void getVehicleID1() {
        Mockito.when(mock.getVehicle()).thenReturn(mock_vehicle);
        Assert.isInstanceOf(Vehicle.class, mock.getVehicle());
    }

    @Test
    public void setVehicleID1() {
        helper.setVehicle(mock_vehicle);
        Assert.isInstanceOf(Vehicle.class, helper.getVehicle());
    }
}