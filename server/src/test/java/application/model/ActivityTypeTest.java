package application.model;

import application.model.ActivityType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityTypeTest {

    ActivityType helper = new ActivityType(3, "Using a bike");
    @Test
    public void getType_id() {
        assertEquals(3, helper.getType_id());
    }

    @Test
    public void getName() {
        assertEquals("Using a bike", helper.getName());
    }
}