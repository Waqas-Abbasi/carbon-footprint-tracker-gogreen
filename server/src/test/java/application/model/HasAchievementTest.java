package application.model;

import application.model.Achievement;
import application.model.HasAchievement;
import application.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class HasAchievementTest {

    LocalDate date1 = LocalDate.of(2000,1,1);
    HasAchievement helper = new HasAchievement(1, 3, date1);
    HasAchievement helper2 = new HasAchievement(null,null,null);
    HasAchievement mock = Mockito.mock(HasAchievement.class);
    Achievement mock_achievement = Mockito.mock(Achievement.class);
    User mock_user = Mockito.mock(User.class);

    @Test
    public void getID(){
        Mockito.when(mock.getId()).thenReturn(23);
        assertEquals(23, mock.getId());
    }

    @Test
    public void getAchievementID() {
        assertEquals(1, (int)helper.getAchievementID());
    }

    @Test
    public void setAchievementID() {
        helper2.setAchievementID(2);
        assertEquals(2, (int)helper2.getAchievementID());
    }

    @Test
    public void getUserID() {
        assertEquals(3, (int)helper.getUserID());
    }

    @Test
    public void setUserID() {
        helper2.setUserID(4);
        assertEquals(4, (int)helper2.getUserID());
    }

    @Test
    public void getDate() {
        assertEquals(date1, helper.getDate());
    }

    @Test
    public void setDate() {
        helper2.setDate(date1);
        assertEquals(date1, helper2.getDate());
    }

    @Test
    public void getAchievement() {
        Mockito.when(mock.getAchievement()).thenReturn(mock_achievement);
        org.springframework.util.Assert.isInstanceOf(Achievement.class, mock.getAchievement());
    }

    @Test
    public void setAchievement() {
        helper.setAchievement(mock_achievement);
        org.springframework.util.Assert.isInstanceOf(Achievement.class, helper.getAchievement());
    }

    @Test
    public void getUser() {
        Mockito.when(mock.getUser()).thenReturn(mock_user);
        org.springframework.util.Assert.isInstanceOf(User.class, mock.getUser());
    }

    @Test
    public void setUser() {
        helper.setUser(mock_user);
        org.springframework.util.Assert.isInstanceOf(User.class, helper.getUser());
    }
}