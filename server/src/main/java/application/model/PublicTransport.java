package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * model for the table Public_Transport on our database.
 */
@Entity
@Table(name = "public_transport", schema = "public")
public class PublicTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    private Activity activity;

    @Column(name = "activity_id", nullable = false, insertable = false, updatable = false)
    private Integer activityId;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "vehicle_id", nullable = false, insertable = false, updatable = false)
    private Integer vehicleId;

    @Column(name = "distance", nullable = false)
    private Double distance;

    public PublicTransport() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    protected PublicTransport(Integer vehicleId, Double distance, Integer activityId) {
        this.vehicleId = vehicleId;
        this.distance = distance;
        this.activityId = activityId;
    }

    public PublicTransport(Double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public Integer getActivityID() {
        return activityId;
    }

    public void setActivityID(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getVehicleID() {
        return vehicleId;
    }

    public void setVehicleID(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Public_Transport{"
                + "id=" + id
                + ", activity_id=" + activityId
                + ", vehicle_id=" + vehicleId
                + ", distance=" + distance
                + '}';
    }
}
