package application.repository;

import application.model.HasAchievement;
import application.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HasAchievementRepository extends CrudRepository<HasAchievement, Integer> {

    @Query(value = "SELECT COUNT(*) FROM has_achievement u WHERE u.user_id = ?1 AND u.achievement_id = ?2", nativeQuery = true)
    int findByUser_idAndAchievement_id1(int user_id, int achievement_id);

    //HasAchievement findByUser_idAndAchievement_id(int achievement_id, int user_id);

    @Query(value = "SELECT * FROM has_achievement u WHERE u.user_id = ?1 AND u.achievement_id = ?2", nativeQuery = true)
    HasAchievement findByUser_idAndAchievement_id(int user_id, int achievement_id);

    HasAchievement findById(int id);

    List<HasAchievement> findAllByUser_id(int user_id);

    void deleteById(int has_achievement_id);
}
