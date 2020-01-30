package application.model;

import application.model.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
public class UserTest{


    User helper1 = Mockito.mock(User.class) ;
    User user = new User("mark", "John", "kittens123",3,2000);



    @Test
    public void getId_Mock(){
        Mockito.when(helper1.getId()).thenReturn(3);
        assertEquals(3, helper1.getId());
    }



    @Test
    public void getUsername() {
        assertEquals("mark", user.getUsername());
    }

    @Test
    public void setUsername() {
        user.setUsername("henry");
        assertEquals("henry", user.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("kittens123", user.getPassword());
    }

    @Test
    public void setPassword() {
        user.setPassword("dogs123");
        assertEquals("dogs123", user.getPassword());
    }

    @Test
    public void getCountry() {
        assertEquals(3, user.getCountry());
    }

    @Test
    public void setCountry() {
        user.setCountry(3);
        assertEquals(3, user.getCountry());
    }

    @Test
    public void getFootprint() {
        assertEquals(2000, user.getScore(), 0);
    }

    @Test
    public void setFootprint() {
        user.setScore(3000);
        assertEquals(3000, user.getScore(), 0);
    }

    @Test
    public void getName() {
        assertEquals("John", user.getName());
    }

    @Test
    public void setName() {
        user.setName("Jack");
        assertEquals("Jack", user.getName());
    }
}