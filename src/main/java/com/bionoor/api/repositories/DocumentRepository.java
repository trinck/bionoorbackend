package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

}
