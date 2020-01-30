package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "meal", schema = "public")
public class Meal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //CHECKSTYLE:OFF
    private int ID;
    //CHECKSTYLE:ON

    @ManyToOne
    @JoinColumn(name = "meal_id", referencedColumnName = "id")
    private MealSub mealSub;

    @Column(name = "meal_id", insertable = false, updatable = false)
    private int mealID;

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    private Activity activity;

    @Column(name = "activity_id", insertable = false, updatable = false)
    private int activityID;

    @Column(name = "food_id")
    private int foodID;

    public Meal() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    public Meal(MealSub mealSub, int mealID, int activityId, int foodID) {
        this.mealSub = mealSub;
        this.mealID = mealID;
        this.activityID = activityId;
        this.foodID = foodID;
    }

    public Meal(int foodID) {
        this.foodID = foodID;
    }

    public void setMealSub(MealSub m1) {
        this.mealSub = m1;
        this.mealID = m1.getId();
    }

    public int getID() {
        return ID;
    }

    public MealSub getMealSub() {
        return mealSub;
    }

    public int getMealID() {
        return mealID;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public void setMealID(int mealID) {
        this.mealID = mealID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Meal{"
                + "ID=" + ID
                + ", mealID=" + mealID
                + ", activityID=" + activityID
                + ", foodID=" + foodID
                + '}';
    }
}

