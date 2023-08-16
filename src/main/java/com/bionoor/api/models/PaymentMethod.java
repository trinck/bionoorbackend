package com.bionoor.api.models;



import java.io.Serializable;

import com.bionoor.api.utils.BionoorEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@EntityListeners(BionoorEntityListener.class)
@Builder
public  class PaymentMethod extends GenericBionoorEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(unique = true)
	protected String name;
	protected String description;
	
	@Column(nullable = false)
	protected String ClassProcessing;
	
	
	
}