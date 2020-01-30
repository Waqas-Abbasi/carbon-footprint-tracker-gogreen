package application.repository;

import application.model.Achievement;

import org.springframework.data.repository.CrudRepository;

public interface AchievementRepository extends CrudRepository<Achievement, Integer> {

    Achievement findByAchievementID(int id);
}
