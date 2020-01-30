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
 * model for the table Lowering_Temp on our database.
 */
@Entity
@Table(name = "lowering_temp")
public class LoweringTemp {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    private Activity activity;

    @Column(name = "activity_id", nullable = false, updatable = false, insertable = false)
    private Integer activityId;

    @Column(name = "temp_inside", nullable = false)
    private Double tempInside;

    @Column(name = "temp_outside", nullable = false)
    private Double tempOutside;

    public LoweringTemp() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    protected LoweringTemp(Integer activityId, Double tempInside, Double tempOutside) {
        this.activityId = activityId;
        this.tempInside = tempInside;
        this.tempOutside = tempOutside;
    }

    public LoweringTemp(Double tempInside, Double tempOutside) {
        this.tempInside = tempInside;
        this.tempOutside = tempOutside;
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

    public Double getTemp_Inside() {
        return tempInside;
    }

    public void setTemp_Inside(Double tempInside) {
        this.tempInside = tempInside;
    }

    public Double getTemp_Outside() {
        return tempOutside;
    }

    public void setTemp_Outside(Double tempOutside) {
        this.tempOutside = tempOutside;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
