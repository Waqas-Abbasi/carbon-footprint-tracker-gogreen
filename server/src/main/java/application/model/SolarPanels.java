package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * model for the table SolarPanels on our database.
 */
@Entity
@Table(name = "solar_panels", schema = "public")
public class SolarPanels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @OneToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    private Activity activity;

    @Column(name = "activity_id", nullable = false, insertable = false, updatable = false)
    private Integer activityId;

    @Column(name = "electricity_usage", nullable = false)
    private Double electricityUsage;

    @Column(name = "percantage_saved", nullable = false)
    private Double percentageSaved;

    public SolarPanels() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    protected SolarPanels(Double electricityUsage, Double percentageSaved, Integer activityId) {
        this.electricityUsage = electricityUsage;
        this.percentageSaved = percentageSaved;
        this.activityId = activityId;
    }

    public SolarPanels(Double electricityUsage, Double percentageSaved) {
        this.electricityUsage = electricityUsage;
        this.percentageSaved = percentageSaved;
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

    public Double getElectricity_Usage() {
        return electricityUsage;
    }

    public void setElectricity_Usage(Double electricityUsage) {
        this.electricityUsage = electricityUsage;
    }

    public Double getPercentage_Saved() {
        return percentageSaved;
    }

    public void setPercentage_Saved(Double percentageSaved) {
        this.percentageSaved = percentageSaved;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "SolarPanels{"
                + "id=" + id
                + ", activity_id=" + activityId
                + ", electricity_Usage=" + electricityUsage
                + ", percentage_Saved=" + percentageSaved
                + '}';
    }
}
