package application.repository;

import java.util.List;

import application.model.PublicTransport;

import org.springframework.data.repository.CrudRepository;

public interface PublicTransportRepository extends CrudRepository<PublicTransport, Integer> {

    List<PublicTransport> findByVehicle_idAndDistance(int id, double distance);
}
