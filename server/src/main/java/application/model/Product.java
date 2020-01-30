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
 * model for the table Product on our database.
 */
@Entity
@Table(name = "product", schema = "public")
public class Product {

    @Id
    @Column(name = "product_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @OneToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    private Activity activity;

    @Column(name = "activity_id", nullable = false, updatable = false, insertable = false)
    private Integer activityId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "continent_id", referencedColumnName = "continent_id")
    private Continent continent;

    @Column(name = "continent_id", nullable = false, updatable = false, insertable = false)
    private Integer continentId;

    public Product() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    protected Product(Integer activityId, String name, Integer continentId) {
        this.activityId = activityId;
        this.name = name;
        this.continentId = continentId;
    }

    public Product(String name) {
        this.name = name;
    }

    public Integer getProductID() {
        return productId;
    }

    public Integer getActivityID() {
        return activityId;
    }

    public void setActivityID(Integer activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getContinentID() {
        return continentId;
    }

    public void setContinentID(Integer continentId) {
        this.continentId = continentId;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }
}
