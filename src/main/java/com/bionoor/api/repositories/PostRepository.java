package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Post;
import com.bionoor.api.models.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}

