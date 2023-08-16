package com.bionoor.api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bionoor.api.admin.AdminProduct.InputProductDto;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Product;
public interface ProductServiceIn {

	
public Product add(Product product ) ;
public Product add(InputProductDto toSave);

public List<Product> allProducts();
public String delete(Long id);

public Product modify(Product product) ;

public Product findByName(String name);
public Product findByCode(String code);
public Product getById(Long id);

public Page<Product> pagesById(int page, int size, Long id, String sort);
public Page<Product> pagesByCode( int page, int size, String sort,String code) ;
public Page<Product> pagesByName( int page, int size, String sort,String name) ;

}
