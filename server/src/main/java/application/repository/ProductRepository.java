package application.repository;

import application.model.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {


  List<Product> findByName(String name);

}
