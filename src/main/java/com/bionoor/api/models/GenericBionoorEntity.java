package com.bionoor.api.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.utils.BionoorEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(BionoorEntityListener.class)
public abstract class GenericBionoorEntity {

	
	@DateTimeFormat(pattern = "yyy-MM-dd hh:mm:ss")
	protected Date createdAt;
	
	@DateTimeFormat(pattern = "yyy-MM-dd hh:mm:ss")
	protected Date modifiedAt;
	
	
	 private String createdBy;
	    
	  private String modifiedBy;
}
