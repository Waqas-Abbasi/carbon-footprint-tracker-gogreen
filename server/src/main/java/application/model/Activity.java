package application.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activity", schema = "public")
public class Activity {

    @Id
    @Column(name = "activity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityID;

    @ManyToOne
    @JoinColumn(name = "activity_type", referencedColumnName = "type_id")
    private ActivityType type;

    @Column(name = "activity_type", insertable = false, updatable = false)
    private int activityType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "user_id", updatable = false, insertable = false)
    private int userId;

    @Column(name = "score", nullable = false)
    private double score;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

    public Activity() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    public Activity(double score, Date date, Time time) {
        this.score = score;
        this.date = date;
        this.time = time;
    }

    protected Activity(int activityType, int userId, double score, Date date, Time time) {
        this.activityType = activityType;
        this.userId = userId;
        this.score = score;
        this.date = date;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getActivityID() {
        return activityID;
    }


    public void setActivityID(int activity_id) {
        this.activityID = activityID;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setType(int activityType) {
        this.activityType = activityType;
    }

    public int getUserID() {
        return userId;
    }

    public void setUserID(int userId) {
        this.userId = userId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setActivityType(ActivityType activityType) {
        this.type = activityType;
    }

    public ActivityType getActivity_Type() {
        return type;
    }

    @Override
    public String toString() {
        return "Activity{"
                + "activity_id=" + activityID
                + ", activity_type=" + activityType
                + ", user_id=" + userId
                + ", score=" + score
                + ", date=" + date
                + ", time=" + time
                + '}';
    }
}

