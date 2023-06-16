package com.bionoor.api.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.utils.BionoorEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;


@Data
@MappedSuperclass
public abstract class GenericBionoorEntity {

	
	@DateTimeFormat(pattern = "yyy-MM-dd hh:mm:ss")
	protected Date createdAt;
	
	@DateTimeFormat(pattern = "yyy-MM-dd hh:mm:ss")
	protected Date modifiedAt;
}
