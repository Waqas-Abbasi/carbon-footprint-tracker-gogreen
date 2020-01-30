package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "food")
public class Food {

    @Id
    @Column(name = "food_id")
    private int foodID;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "score")
    private double score;

    protected Food() {
    }

    public Food(String foodName, Double score) {
        this.foodName = foodName;
        this.score = score;
    }

    public int getFoodID() {
        return foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Food{"
                + "foodID=" + foodID
                + ", foodName='" + foodName + '\''
                + ", score=" + score
                + '}';
    }
}
