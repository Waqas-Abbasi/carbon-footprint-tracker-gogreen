package application.repository;

import application.model.Continent;
import org.springframework.data.repository.CrudRepository;

public interface ContinentRepository extends CrudRepository<Continent, Integer> {


  Continent findById(int id);

  Continent findByName(String name);
}
