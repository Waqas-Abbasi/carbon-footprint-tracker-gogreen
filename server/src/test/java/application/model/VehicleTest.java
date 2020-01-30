package application.model;

import application.model.Vehicle;
import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleTest {

    Vehicle helper = new Vehicle(1, "Car", 45.9);

    @Test
    public void getVehicleID() {
        assertEquals(1, (int)helper.getVehicleID());
    }

    @Test
    public void getName() {
        assertEquals("Car", helper.getName());
    }

    @Test
    public void getCarbon_Avoided() {
        assertEquals(45.9, helper.getCarbon_Avoided(), 0);
    }
}