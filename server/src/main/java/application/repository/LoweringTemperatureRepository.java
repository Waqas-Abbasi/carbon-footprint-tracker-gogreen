package application.repository;

import application.model.LoweringTemp;
import org.springframework.data.repository.CrudRepository;

public interface LoweringTemperatureRepository extends CrudRepository<LoweringTemp, Integer> {
}
