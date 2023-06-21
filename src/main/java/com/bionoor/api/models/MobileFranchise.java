package com.bionoor.api.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue(value = "Mobile_franchise")
public class MobileFranchise extends Customer{

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer", fetch =  FetchType.LAZY)
	 private List<Document> docs = new ArrayList<>();
	
}
