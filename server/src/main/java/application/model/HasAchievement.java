package application.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * model for the table HasAchievement on our database.
 */

@Entity
@Table(name = "has_achievement")
public class HasAchievement {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "achievement_id", referencedColumnName = "achievement_id")
    private Achievement achievement;

    @Column(name = "achievement_id", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer achievement_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Integer user_id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public HasAchievement() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    public HasAchievement(Integer achievement_id, Integer user_id, LocalDate date) {
        this.achievement_id = achievement_id;
        this.user_id = user_id;
        this.date = date;
    }

    public HasAchievement(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Integer getAchievementID() {
        return achievement_id;
    }

    public void setAchievementID(Integer achievement_id) {
        this.achievement_id = achievement_id;
    }

    public Integer getUserID() {
        return user_id;
    }

    public void setUserID(Integer user_id) {
        this.user_id = user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
