package com.bionoor.api.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Product;
import com.opencsv.CSVWriter;
@Service
public class CsvGeneratorImpl  implements CsvGeneratorIn{

	@Autowired
	private ProductServiceIn productServiceIn;
	
	 public byte[] generateCsvProducts()  {
	        List<Product> products = productServiceIn.allProducts();

	        // Create a temporary file to store the CSV data
	       

	        try  {
	        	
	        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	             OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
	             CSVWriter csvWriter = new CSVWriter(writer);
	        	
	        	//File tempFile = File.createTempFile("products", ".csv");
	        	//CSVWriter writer = new CSVWriter(new FileWriter(tempFile));
	            // Write header
	            String[] header = {"ID", "Name", "Code","Steps", "CreatedAt", "Description", "Price", "Quantity", "Promotion price","Category_id", "IsOnSale"};
	            csvWriter.writeNext(header);

	            // Write data
	            for (Product product : products) {
	                String[] data = {String.valueOf(product.getId()), product.getName(), product.getCode(), product.getSteps(),product.getCreateAt().toString(),product.getDescription(),String.valueOf(product.getPrice()), String.valueOf(product.getQuantity()),String.valueOf(product.getPromotion()),String.valueOf( product.getCategory().getId()),String.valueOf(product.isOnSale())};
	                csvWriter.writeNext(data);
	            }
	            
	            
	            // Flush the writer to ensure all data is written
	            csvWriter.flush();
	            
	            return outputStream.toByteArray();
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
	            e.printStackTrace();
	        }
			
	        
	       
	        return null;
	        
	        // Return the CSV file as a resource
	       
	    }
	
}
