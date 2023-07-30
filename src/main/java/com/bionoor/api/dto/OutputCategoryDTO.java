package com.bionoor.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputCategoryDTO implements Serializable {

	


	private String name; // name of the category
	
	private String image; 
	
    private OutputCategoryDTO parentCategory;
    
	/*
	 * // The child categories of this category
	 * 
	 * @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.PERSIST)
	 * private List<OutputCategoryDTO> subCategories = new
	 * ArrayList<OutputCategoryDTO>();;
	 */
 
	private List<OutputProductDTO> products = new ArrayList<OutputProductDTO>(); // list of products in the category
	
	
	public OutputCategoryDTO(Category category) {
			
			this.name = category.getName();
	        this.image = category.getImage();
	        this.parentCategory = category.getParentCategory() != null?  new OutputCategoryDTO(category.getParentCategory()): null;
	        category.getProducts().forEach(p ->   this.products.add(new OutputProductDTO(p)));
	       
	}

}
