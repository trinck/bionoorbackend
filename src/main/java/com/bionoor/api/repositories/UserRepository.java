package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
