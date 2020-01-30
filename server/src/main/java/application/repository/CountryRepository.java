package application.repository;

import application.model.Country;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    Country findByName(String name);

    Country findByCountryId(int id);

}
