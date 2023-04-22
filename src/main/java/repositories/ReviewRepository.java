package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
