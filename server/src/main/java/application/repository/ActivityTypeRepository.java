package application.repository;

import application.model.ActivityType;

import org.springframework.data.repository.CrudRepository;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Integer> {

    ActivityType findById(int id);
}
