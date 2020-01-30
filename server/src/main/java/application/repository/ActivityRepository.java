package application.repository;

import application.model.Activity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Integer> {

    Activity findActivitiesByUser_id(int id);

    @Query(value = "SELECT * "
            + "FROM activity u "
            + "WHERE u.activity_type = ?1 "
            + "ANd u.user_id = ?2", nativeQuery = true)
    Activity findByActivity_TypeAndUser_id(int activityType, int user_id);

    Activity findByActivityID(int id);

    @Query(value = "SELECT * "
            + "FROM activity u "
            + "WHERE u.activity_type = ?1 "
            + "ANd u.user_id = ?2", nativeQuery = true)
    List<Activity> findByActivity_TypeAndUser_id1(int activityType, int user_id);

    List<Activity> findByUser_idOrderByDateDesc(int id);

    //Nafie's querries

    @Query(value = "SELECT COUNT(*) "
            + "FROM activity u "
            + "WHERE u.user_id = ?1 "
            + "AND u.activity_type = ?2", nativeQuery = true)
    int CountAllByUserAndActivity_Type(int user_id, int activity_type);

    @Query(value = "SELECT COUNT(*) FROM activity u WHERE u.user_id = ?1", nativeQuery = true)
    int countAllByUser(int user_id);

//    @Query("SELECT a FROM Activity a where a.user_id=?1 ORDER BY a.date")
//    List<Activity> recentActivities(int id);
}
