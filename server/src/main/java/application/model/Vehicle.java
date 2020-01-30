package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model for the table Vehicle on our database.
 */
@Entity
@Table(name = "vehicle", schema = "public")
public class Vehicle {

    @Id
    @Column(name = "vehicle_id", nullable = false, unique = true)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "score", nullable = false)
    private Double score;

    public Vehicle() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    protected Vehicle(Integer vehicleId, String name, Double score) {
        this.id = vehicleId;
        this.name = name;
        this.score = score;
    }

    public int getVehicleID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getCarbon_Avoided() {
        return score;
    }

    @Override
    public String toString() {
        return "Vehicle{"
                + "vehicle_id=" + id
                + ", name='" + name + '\''
                + ", score=" + score
                + '}';
    }
}
