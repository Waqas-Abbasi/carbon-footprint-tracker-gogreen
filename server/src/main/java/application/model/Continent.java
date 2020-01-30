package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model for the table Continent on our database.
 */
@Entity
@Table(name = "continent", schema = "public")
public class Continent {

    @Id
    @Column(name = "continent_id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public Continent() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    protected Continent(Integer continentId, String name) {
        this.id = continentId;
        this.name = name;
    }

    public Integer getContinent_id() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Continent{"
                + "id=" + id + ", name='" + name + '}';
    }
}
