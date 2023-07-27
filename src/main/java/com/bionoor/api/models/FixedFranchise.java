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
@DiscriminatorValue(value = "Fixed_franchise")
public class FixedFranchise extends Customer {

	
	
	
	 private String storeAddress;
	   /** @pdOid 955441fd-533a-4f3f-8b56-3020a9cc946e */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer",fetch = FetchType.LAZY)
	 private List<Document> docs = new ArrayList<>();
	  
}
