package application.model;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class FriendWithTest {

    FriendWith mock = Mockito.mock(FriendWith.class);
    FriendWith mock2 = new FriendWith();
    User user = Mockito.mock(User.class);

    @Test
    public void getId() {
        Mockito.when(mock.getId()).thenReturn(3);
        assertEquals(3, mock.getId());
    }

    @Test
    public void getUser_id_1() {
        Mockito.when(mock.getUser_id_1()).thenReturn(3);
        assertEquals(3, mock.getUser_id_1());
    }

    @Test
    public void setUser_id_1() {
        mock2.setUser_id_1(3);
        assertEquals(3, mock2.getUser_id_1());
    }

    @Test
    public void getUser_id_2() {
        Mockito.when(mock.getUser_id_2()).thenReturn(3);
        assertEquals(3, mock.getUser_id_2());
    }

    @Test
    public void setUser_id_2() {
        mock2.setUser_id_2(3);
        assertEquals(3, mock2.getUser_id_2());
    }

    @Test
    public void getUser1() {
        Mockito.when(mock.getUser1()).thenReturn(user);
        assertEquals(user, mock.getUser1());
    }

    @Test
    public void setUser1() {
        mock2.setUser1(user);
        assertEquals(user, mock2.getUser1());
    }

    @Test
    public void getUser2() {
        Mockito.when(mock.getUser2()).thenReturn(user);
        assertEquals(user, mock.getUser2());
    }

    @Test
    public void setUser2() {
        mock2.setUser2(user);
        assertEquals(user, mock2.getUser2());
    }
}