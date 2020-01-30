package application.repository;

import application.model.ActivityType;
import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<ActivityType, Integer> {
}
