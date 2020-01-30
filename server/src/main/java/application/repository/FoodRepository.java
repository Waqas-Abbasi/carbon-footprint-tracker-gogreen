package application.repository;

import application.model.Food;

import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Integer> {
    Food findByFoodName(String foodName);

    Food findByFoodID(int foodID);
}
