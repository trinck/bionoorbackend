package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

}
