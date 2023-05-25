package com.bionoor.api.services;

import java.util.List;

import com.bionoor.api.admin.AdminProduct.InputProductDto;
import com.bionoor.api.models.Product;
public interface ProductServiceIn {

	
public Product add(Product product ) ;
public Product add(InputProductDto toSave);

public List<Product> allProducts();
public String delete(Long id);

public Product modify(Product product) ;

public Product getById(Long id);
}
