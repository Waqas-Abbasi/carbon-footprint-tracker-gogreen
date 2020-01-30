package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model for the table Continent_Distance on our database.
 */
@Entity
@Table(name = "Continent_Distance", schema = "public")
public class ContinentDistance {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "from", nullable = false)
    private Integer from;

    @Column(name = "to", nullable = false)
    private Integer to;

    @Column(name = "score", nullable = false)
    private Double score;

    public ContinentDistance() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    protected ContinentDistance(Integer from, Integer to, Double score) {
        this.from = from;
        this.to = to;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

    public Double getScore() {
        return score;
    }

}
