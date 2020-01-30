package application.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.json.JSONObject;
import org.springframework.stereotype.Indexed;

@Indexed
@Entity
@Table(name = "user_info")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "country_id")
    private int country;

    @Column(name = "score")
    private double score;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String name, String password, Integer countryId, double score) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.country = countryId;
        this.score = score;
    }

//    public User() {}
//
//    /**
//     * Instantiates a new Customer.
//     *
//     * @param username the first name
//     * @param password  the password
//     * @param country   the country
//     * @param score the score
//     */
//
//    public User(String username, String name, String password, int country, double score) {
//        this.username = username;
//        this.name = name;
//        this.password = password;
//        this.country = country;
//        this.score = score;
//    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, name='%s', username='%s', password='%s', country='%s', score=" + score + "]",
                id, name, username, password, country, score);
    }

    public JSONObject toJson() {
        JSONObject cred = new JSONObject();
        cred.put("username", username);
        cred.put("country", country);
        cred.put("footprint", score);
        cred.put("name", name);

        return cred;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

