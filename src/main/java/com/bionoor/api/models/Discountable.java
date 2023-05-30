package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Discountable implements Serializable {

	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToMany
	@JoinTable(
			name = "discountable_discountCode",
			joinColumns = @JoinColumn(name="discountable_id"),
			
			inverseJoinColumns = @JoinColumn(name="discountCode_id")
			)
	private List<DiscountCode> discountables = new ArrayList<>();
}
