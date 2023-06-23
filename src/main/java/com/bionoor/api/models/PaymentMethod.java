package com.bionoor.api.models;


import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.utils.BionoorEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
@EntityListeners(BionoorEntityListener.class)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "method")
public abstract class PaymentMethod extends GenericBionoorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	protected String description;
	
	@Column(name = "method", insertable = false, updatable = false)
    protected String discriminatorValue;
	
	
	
	
}