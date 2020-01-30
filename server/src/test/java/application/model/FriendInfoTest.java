package application.model;

import application.model.Friendinfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendInfoTest {

    Friendinfo test1 = new Friendinfo("John", 34);
    Friendinfo test2 = new Friendinfo(null, 0);

    @Test
    void getUsername() {
        assertEquals("John", test1.getUsername());
    }

    @Test
    void getScore() {
        assertEquals(34, test1.getScore());
    }

    @Test
    void setUsername() {
        test2.setUsername("John");
        assertEquals("John", test2.getUsername());
    }

    @Test
    void setScore() {
        test2.setScore(34);
        assertEquals(34, test2.getScore());
    }
}