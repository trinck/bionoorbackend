package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Certificat;
import com.bionoor.api.models.Product;

public interface CertificatRepository extends JpaRepository<Certificat, Long>{

}
