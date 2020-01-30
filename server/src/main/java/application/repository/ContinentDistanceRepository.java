package application.repository;

import application.model.ContinentDistance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContinentDistanceRepository extends CrudRepository<ContinentDistance, Integer> {

    @Query("SELECT score FROM ContinentDistance c where c.from=?1 and c.to=?2")
    double getId(int userId1, int userId2);

    @Query("SELECT score FROM ContinentDistance c where c.from=?1 and c.to=?2")
    double getScore(int continentId1, int continentId2);
}
