package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue(value = "Distributor")
public class Distributor extends Customer  {

	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer", fetch = FetchType.LAZY)
	 private List<Document> docs = new ArrayList<>();
}
