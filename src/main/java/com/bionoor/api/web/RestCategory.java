package com.bionoor.api.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.OutputCategoryDTO;
import com.bionoor.api.dto.OutputProductDTO;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Product;
import com.bionoor.api.services.CategoryService;
import com.bionoor.api.services.CategoryServiceIn;
import com.bionoor.api.services.CsvGeneratorIn;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@CrossOrigin("*")

@RequestMapping("api/categories")
public class RestCategory {

	@Autowired
	private CsvGeneratorIn csvGeneratorIn;
	
	@Autowired
	private CategoryServiceIn categoryService;
	
	
	@PostMapping(value = "/put/name")
	public OutputCategoryDTO  putName(@Valid @ModelAttribute CategoryNameDto categoryNameDto) {
		
		
		 Category category = this.categoryService.getById(categoryNameDto.getId());
		 category.setName(categoryNameDto.getName()); 
		 
		 category =  this.categoryService.add(category);
		 
		return new OutputCategoryDTO(category); 
	}
	
	@GetMapping(value = "/")
	public List<OutputCategoryDTO>  categories() {
		
		 List<Category> categories = this.categoryService.allCategories(); 
		List<OutputCategoryDTO> categoryOutputDtos = new ArrayList<>();	
		for( Category category : categories) {
			categoryOutputDtos.add(new OutputCategoryDTO(category));
		}
		
		return categoryOutputDtos;
	}
	
	
	@GetMapping(value = "/{id}")
	public OutputCategoryDTO  category(@PathVariable(value = "id") Long id) {
		
		 Category category = this.categoryService.getById(id);	
		return new OutputCategoryDTO(category);
	}
	
	
	@GetMapping(value = "/graphs/{id}")
	@ResponseBody
	public  Map<String, Object>  graphs(@PathVariable(value = "id") Long id) {
		 Map<String, Object> data = this.categoryService.getDataGraphs(id);
		 
		 OutputCategoryDTO category =	new OutputCategoryDTO((Category)data.get("category"));
		 
		 List<OutputProductDTO> outputProductDTOs = new ArrayList<>();	
			for( Product product :(List<Product>) data.get("products")) {
				outputProductDTOs.add(new OutputProductDTO(product));
			}
		 
			Map<String, Object> body = Map.of("products",outputProductDTOs, "category", category);
			
		return body;
	}
	
	
	@GetMapping(value = "/delete")
	public OutputCategoryDTO  delete(@RequestParam Long id) {
		
		 Category category = this.categoryService.delete(id);	
		return new OutputCategoryDTO(category);
	}
	
	
	@PostMapping(value = "/put/image")
	public ResponseEntity<String>  putImage(@Valid @ModelAttribute CategoryimageDto categoryimageDto) {
	
		 Category category = this.categoryService.getById(categoryimageDto.getId());
		 category.setImage(categoryimageDto.getImage()); 
		
		return new ResponseEntity<String>(this.categoryService.add(category).getImage(), HttpStatus.OK); 
	}
	
	
	
	
	@PostMapping(value = "/put/parent")
	public OutputCategoryDTO  putParent(@Valid @ModelAttribute CategoryParentDto categoryParentDto) {
		
		
		 Category category = this.categoryService.getById(categoryParentDto.getId());
		 Category parent = this.categoryService.getById(categoryParentDto.getParentId());
		 Category oldParent = category.getParentCategory();
		 oldParent.getSubCategories().remove(category);
		 category.setParentCategory(parent);
		 parent.getSubCategories().add(category);
		 this.categoryService.add(parent);
		 this.categoryService.add(oldParent);
		 OutputCategoryDTO categoryOutputDto = new OutputCategoryDTO(parent);
		
		return categoryOutputDto; 
	}
	
	
	
	
	
	@GetMapping("/csv/download-csv")
    public ResponseEntity< byte []> downloadCsvFile() throws IOException  {
        // Generate the CSV file using the CsvGenerator
    	List<Category> listCategory = this.categoryService.allCategories();
    	List<Object> listObject = new ArrayList();
    	listCategory.forEach(p-> listObject.add(p) );    	
        byte [] csvFile = csvGeneratorIn.generateCsv( listObject);

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=category.csv");
       
        // Return the CSV file as a ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csvFile);
    }
	
	
	
	
	
	
	
	
	@Data
	@NoArgsConstructor
	public class CategoryNameDto {
		  
			@NotNull
		    private Long id;
		     @NotBlank @NotEmpty
		    private String name;
	}
	
	
	
	@Data
	@NoArgsConstructor
	public class CategoryParentDto {
		  
			@NotNull
		    private Long parentId;
			@NotNull
			private Long id;
		   
	}
	
	@Data
	@NoArgsConstructor
	public class CategoryimageDto {
		  
			@NotEmpty @NotBlank
		    private String image;
			@NotNull
			private Long id;
		   
	}
}
