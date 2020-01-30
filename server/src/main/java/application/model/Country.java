package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * model for the table Country on our database.
 */
@Entity
public class Country {

  @Id
  @Column(name = "country_id", nullable = false, unique = true)
  private Integer countryId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "average_footprint", nullable = false)
  private Double averageFootprint;

  @ManyToOne
  @JoinColumn(name = "continent_id", referencedColumnName = "continent_id")
  private Continent continent;

  @Column(name = "continent_id", nullable = false, updatable = false, insertable = false)
  private int continentId;

  public Country() {
  }

  /**
   * TODO: Add JavaDoc.
   */
  protected Country(Integer countryId, String name, Double averageFootprint) {
    this.countryId = countryId;
    this.name = name;
    this.averageFootprint = averageFootprint;
  }

  public Integer getCountryId() {
    return countryId;
  }

  public String getName() {
    return name;
  }

  public Double getAverage_Footprint() {
    return averageFootprint;
  }

  public Continent getContinent() {
    return continent;
  }

  public int getContinent_id() {
    return continentId;
  }

  @Override
  public String toString() {
    return "Country{"
      + "countryId=" + countryId
      + ", name='" + name + '\''
      + ", average_Footprint=" + averageFootprint
      + '}';
  }
}
