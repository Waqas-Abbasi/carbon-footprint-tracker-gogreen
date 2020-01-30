package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model for the table Achievement on our database.
 */
@Entity
@Table(name = "achievement", schema = "public")
public class Achievement {

    @Id
    @Column(name = "achievement_id", nullable = false, unique = true)
    private int achievementID;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    /**
     * TODO: Add JavaDoc.
     */
    public Achievement() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    public Achievement(Integer achievementID, String title, String description) {
        this.achievementID = achievementID;
        this.title = title;
        this.description = description;
    }

    public Integer getAchievementID() {
        return achievementID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{"
                + "\"achievementID\":"
                + achievementID
                + ",\"title\":\""
                + title
                + "\",\"description\":\""
                + description
                + "\""
                + "}";
    }
}
