package application.repository;

import application.model.Meal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal, Integer> {

    List<Meal> findByMealID(int mealID);

    List<Meal> findByActivityID(int activityID);

}