package application.repository;

import application.model.FriendWith;
import application.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendWithRepository extends CrudRepository<FriendWith, Integer> {

  FriendWith findByUser1AndUser2(User user1, User user2);

  List<FriendWith> findByUser1(User user1);

  List<FriendWith> findByUser2(User user2);

  List<FriendWith> findByUser1OrUser2(User user1, User user2);

  @Query(value = "SELECT COUNT(*) FROM friend_with u WHERE u.user_id_1 = ?1 OR u.user_id_2 = ?2", nativeQuery = true)
  int countAllByUser1OrUser2(int user_id_1, int user_id_2);

  void deleteFriend_withById(int id);
}
