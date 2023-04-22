package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
