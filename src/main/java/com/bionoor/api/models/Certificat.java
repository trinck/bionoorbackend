package com.bionoor.api.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "cetificats")
public class Certificat implements Serializable{

	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	   /** @pdOid 881cf904-8c41-4a8a-b4c5-e696c230b105 */
	   @Column(nullable = false)
	   private String icon;
	   /** @pdOid 12ace58d-7b87-4d9a-8644-9f7a9690718e */
	   private String description;
	   /** @pdOid e29ba99a-c30c-40ff-9847-ae5d9237ecd5 */
	   
	   @Column(unique = true, nullable = false)
	   private String title;
	   
	   
	   @ManyToMany
	    @JoinTable(
	      name = "certificat_product", 
	      joinColumns = @JoinColumn(name = "certificat_id"), 
	      inverseJoinColumns = @JoinColumn(name = "product_id"))
	   List<Product> products;
}
