package application.repository;

import application.model.Vehicle;

import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {

    Vehicle findById(int id);

    Vehicle findByName(String name);
}
