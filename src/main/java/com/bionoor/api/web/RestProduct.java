package com.bionoor.api.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.OutputProductDTO;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Certificat;
import com.bionoor.api.models.Media;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.ProductRange;
import com.bionoor.api.models.Review;
import com.bionoor.api.services.CsvGeneratorIn;
import com.bionoor.api.services.ProductService;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
public class RestProduct {

	@Autowired
	private ProductService productService;
	@Autowired
	private CsvGeneratorIn csvGeneratorIn;
	@GetMapping(value = "/search")
	public OutputProductDTO search(@RequestParam @NotEmpty String name) {
		
		return  new OutputProductDTO(this.productService.findByName(name));
	}
	
	@GetMapping(value = "/all")
	public List<OutputProductDTO> AllProduct() {
		
		
		List<Product> list = this.productService.allProducts();
		List<OutputProductDTO> productDTOs = new ArrayList<>();
		
		list.forEach(p ->{
			productDTOs.add(new OutputProductDTO(p));
		});
		
		return productDTOs; 
	}
	
	
	
	
	
	
	@GetMapping("/download-csv")
    public ResponseEntity< byte []> downloadCsvFile() throws IOException  {
        // Generate the CSV file using the CsvGenerator
        byte [] csvFile = csvGeneratorIn.generateCsvProducts();

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product.csv");
       
        // Return the CSV file as a ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csvFile);
    }
	
}

