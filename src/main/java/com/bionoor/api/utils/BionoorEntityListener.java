package com.bionoor.api.utils;

import java.util.Date;

import com.bionoor.api.models.GenericBionoorEntity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class BionoorEntityListener {

	
	
	@PrePersist
	public void prePersist(GenericBionoorEntity bionoorEntity) {
		
		bionoorEntity.setCreatedAt(new Date());
	}
	
	
	@PreUpdate
	public void preUpdate(GenericBionoorEntity bionoorEntity) {
		
		bionoorEntity.setModifiedAt(new Date());
	}
}
