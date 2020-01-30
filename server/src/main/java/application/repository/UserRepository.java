package application.repository;

import application.model.User;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findByName(String name);

    List<User> findById(int id);

    List<User> findByCountry(String country);

    List<User> findAll(Sort sort);

    @Modifying
    @Query("update User u set u.score = ?1 where u.username = ?2")
    void setUserScoreByUsername(double score, String username);

    User findByNameLikeIgnoreCase(String name);

    List<User> findByNameStartsWithIgnoreCase(String name);

    void deleteUserByUsername(String username);

}
