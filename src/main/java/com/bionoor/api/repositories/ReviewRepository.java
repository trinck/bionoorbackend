package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
