package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
