package application.model;

import application.model.Achievement;
import org.junit.Test;

import static org.junit.Assert.*;

public class AchievementTest {

    Achievement helper = new Achievement(1, "greenMaster", "User has score more than 500" );

    @Test
    public void getAchievementID() {
        assertEquals( 1, (int) helper.getAchievementID());
    }

    @Test
    public void getTitle() {
        assertEquals("greenMaster", helper.getTitle());
    }

    @Test
    public void getDescription() {
        assertEquals("User has score more than 500", helper.getDescription());
    }

}