package com.bionoor.api.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Document extends GenericBionoorEntity{
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	   /** @pdOid d2e066fe-5956-4664-b619-3c2a915b378a */
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "customer_id", nullable = false)
		private Customer customer;
		
		private boolean verified;
		
	   private String name;
	   
	   private String type;
	   
	   private Long size;
	   
	   private String referenceDoc;
	   /** @pdOid 16db48da-f010-4616-9b54-87a354d3c34f */
	   private String description;
	   /** @pdOid 6494af43-2536-4c19-a656-aa82e574ffe1 */
	   @Column(nullable = false)
	   private String url;
}
