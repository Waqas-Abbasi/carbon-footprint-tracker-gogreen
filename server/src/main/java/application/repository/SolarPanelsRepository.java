package application.repository;

import application.model.SolarPanels;
import org.springframework.data.repository.CrudRepository;

public interface SolarPanelsRepository extends CrudRepository<SolarPanels, Integer> {
}
